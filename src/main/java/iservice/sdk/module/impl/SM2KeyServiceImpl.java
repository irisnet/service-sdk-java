package iservice.sdk.module.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.mindrot.jbcrypt.BCrypt;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
import org.bouncycastle.bcpg.ArmoredInputStream;
import com.codahale.xsalsa20poly1305.SimpleBox;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.NoSuchElementException;

import iservice.sdk.entity.Key;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.util.Bip39Utils;
import iservice.sdk.util.SM2Utils;
import static iservice.sdk.module.impl.BCryptImpl.decode_base64;
import static iservice.sdk.module.impl.BCryptImpl.encode_base64;

public class SM2KeyServiceImpl extends AbstractKeyServiceImpl {

  private static final int LOG_ROUNDS = 12;
  private static final int REAL_SALT_BEGIN_POS = 7;
  private static final int REAL_SALT_BASE64_LEN = 22;
  private static final String PREFIX_SALT = "$2a$12$";
  private static final String ALGO_TYPE = "sm2";
  private static final String PRIV_KEY_NAME = "cosmos/PrivKeySm2";

  public SM2KeyServiceImpl(IKeyDAO keyDAO) {
    super(keyDAO);
  }

  @Override
  public Mnemonic addKey(String name, String password) throws ServiceSDKException {
    String mnemonic = Bip39Utils.generateMnemonic();
    DeterministicKey dk = super.generateDeterministicKey(mnemonic);

    ECPoint pubkey = SM2Utils.getPublicKeyFromPrivkey(dk.getPrivKey());

    byte[] encoded = pubkey.getEncoded(true);
    byte[] hash = Hash.sha256(encoded);
    byte[] pre20 = new byte[20];
    System.arraycopy(hash, 0, pre20, 0, 20);
    String addr = super.toBech32(pre20);
    super.saveKey(name, password, addr, dk.getPrivKeyBytes());
    return new Mnemonic(addr, mnemonic);
  }

  @Override
  public String recoverKey(String name, String password, String mnemonic, boolean derive, int index,
                           String saltPassword) throws ServiceSDKException {
    DeterministicKey dk = super.generateDeterministicKey(mnemonic);
    ECPoint pubkey = SM2Utils.getPublicKeyFromPrivkey(dk.getPrivKey());

    byte[] encoded = pubkey.getEncoded(true);
    byte[] hash = Hash.sha256(encoded);
    byte[] pre20 = new byte[20];
    System.arraycopy(hash, 0, pre20, 0, 20);
    String addr = super.toBech32(pre20);
    super.saveKey(name, password, addr, dk.getPrivKeyBytes());
    return addr;
  }
  @Override
  public String importFromKeystore(String name, String keyPassword, String keystorePassword, String keystore) throws ServiceSDKException, IOException {

    InputStream inputStream = new FileInputStream(keystore);
    ArmoredInputStream aIS = new ArmoredInputStream(inputStream);
    String[] headers = aIS.getArmorHeaders();
    Hashtable<String,String> headersTable = new Hashtable<>();
    for (String headersItem : headers){
      String[] itemSplit = headersItem.split(": ");
      headersTable.put(itemSplit[0], itemSplit[1]);
    }
    byte[] encBytes = new byte[77];
    aIS.read(encBytes);

    byte[] realSaltByte = Hex.decode(headersTable.get("salt"));
    String realSaltString = encode_base64(realSaltByte, 16);
    String salt = PREFIX_SALT + realSaltString;

    String keyHash = BCrypt.hashpw(keystorePassword, salt);
    byte[] keyHashByte = keyHash.getBytes(StandardCharsets.UTF_8);
    byte[] keyHashSha256 = Hash.sha256(keyHashByte);

    SimpleBox box = new SimpleBox(keyHashSha256);
    byte[] privKeyAmino = box.open(encBytes).get();
    byte[] privKeyTemp= Arrays.copyOfRange(privKeyAmino, 5, privKeyAmino.length);

    BigInteger privKey = new BigInteger(1,privKeyTemp);
    ECPoint pubkey = SM2Utils.getPublicKeyFromPrivkey(privKey);

    byte[] encoded = pubkey.getEncoded(true);
    byte[] hash = Hash.sha256(encoded);
    byte[] pre20 = new byte[20];
    System.arraycopy(hash, 0, pre20, 0, 20);
    String addr = super.toBech32(pre20);
    super.saveKey(name, keyPassword, addr, Utils.bigIntegerToBytes(privKey, 32));
    return addr;
  }

  @Override
  public String exportKeystore(String name, String keyPassword, String keystorePassword, File destinationDirectory) throws ServiceSDKException, IOException {
    Key key = super.getKey(name, keyPassword);
    byte[] privKeyTemp = key.getPrivKey();
    byte[] prefixAmino = getPrefixAmino(PRIV_KEY_NAME);
    byte[] privKeyAmino = ArrayUtils.addAll(prefixAmino,privKeyTemp);

    String salt = BCrypt.gensalt(LOG_ROUNDS);
    String keyHash = BCrypt.hashpw(keystorePassword, salt);
    byte[] keyHashByte = keyHash.getBytes(StandardCharsets.UTF_8);
    byte[] keyHashSha256 = Hash.sha256(keyHashByte);

    SimpleBox box = new SimpleBox(keyHashSha256);
    byte[] encBytes = box.seal(privKeyAmino);

    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    ArmoredOutputStreamImpl aOS = new ArmoredOutputStreamImpl(byteStream);
    String realSaltString = salt.substring(REAL_SALT_BEGIN_POS, REAL_SALT_BEGIN_POS+REAL_SALT_BASE64_LEN);
    byte[] realSaltByte = decode_base64(realSaltString, 16);
    aOS.setHeader("salt", Hex.toHexString(realSaltByte).toUpperCase());
    aOS.setHeader("kdf", "bcrypt");
    aOS.setHeader("type", ALGO_TYPE);
    aOS.write(encBytes);
    aOS.close();
    return writeArmorToFile(destinationDirectory, key.getAddress(), byteStream.toString().trim());
  }
}

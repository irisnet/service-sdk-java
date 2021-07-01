package irita.sdk.module.keys;

import com.codahale.xsalsa20poly1305.SimpleBox;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.crypto.ArmoredInputStream;
import irita.sdk.module.crypto.ArmoredOutputStreamImpl;
import irita.sdk.util.Bech32Utils;
import irita.sdk.util.Bip44Utils;
import irita.sdk.util.HashUtils;
import irita.sdk.util.SM2Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.Security;
import java.util.Arrays;
import java.util.Hashtable;

import static irita.sdk.constant.Armored.*;
import static irita.sdk.module.crypto.BCryptImpl.decode_base64;
import static irita.sdk.module.crypto.BCryptImpl.encode_base64;

public class KeyManager implements Key {
    private final BigInteger privKey;
    private final String addr;
    private String mnemonic;
    // this KEY_PATH just for iris
    private static final String KEY_PATH = "m/44'/118'/0'/0/0";
    private static final String HRP = "iaa";

    public KeyManager(String mnemonic) {
        byte[] seed = Bip44Utils.getSeed(mnemonic);
        DeterministicKey dk = Bip44Utils.getDeterministicKey(mnemonic, seed, KEY_PATH);
        BigInteger privKey = dk.getPrivKey();
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);

        byte[] encoded = publicKey.getEncoded(true);
        byte[] hash = HashUtils.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);

        this.addr = Bech32Utils.toBech32(HRP, pre20);
        this.privKey = privKey;
        this.mnemonic = mnemonic;
    }

    public KeyManager(BigInteger privKey) {
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);
        byte[] encoded = publicKey.getEncoded(true);
        byte[] hash = HashUtils.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);

        this.addr = Bech32Utils.toBech32(HRP, pre20);
        this.privKey = privKey;
    }

    public KeyManager(InputStream keystore, String password) throws IOException {
        ArmoredInputStream aIS = new ArmoredInputStream(keystore);
        String[] headers = aIS.getArmorHeaders();
        Hashtable<String, String> headersTable = new Hashtable<>();
        for (String headersItem : headers) {
            String[] itemSplit = headersItem.split(": ");
            headersTable.put(itemSplit[0], itemSplit[1]);
        }
        byte[] encBytes = new byte[77];
        aIS.read(encBytes);

        byte[] realSaltByte = Hex.decode(headersTable.get("salt"));
        String realSaltString = encode_base64(realSaltByte, 16);
        String salt = PREFIX_SALT + realSaltString;

        String keyHash = BCrypt.hashpw(password, salt);
        byte[] keyHashByte = keyHash.getBytes(StandardCharsets.UTF_8);
        byte[] keyHashSha256 = HashUtils.sha256(keyHashByte);

        SimpleBox box = new SimpleBox(keyHashSha256);
        byte[] privKeyAmino = box.open(encBytes).get();
        byte[] privKeyTemp = Arrays.copyOfRange(privKeyAmino, 5, privKeyAmino.length);

        BigInteger privKey = new BigInteger(1, privKeyTemp);
        ECPoint pubkey = SM2Utils.getPublicKeyFromPrivkey(privKey);

        byte[] encoded = pubkey.getEncoded(true);
        byte[] hash = HashUtils.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);

        this.addr = Bech32Utils.toBech32(HRP, pre20);
        this.privKey = privKey;
    }

    public static Key recoverFromCAKeystore(InputStream keystore, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
        ks.load(keystore, password.toCharArray());
        BCECPrivateKey privateKey = (BCECPrivateKey) ks.getKey("signKey", password.toCharArray());

        return new KeyManager(privateKey.getD());
    }

    public static Key add() throws Exception {
        String mnemonic = Bip44Utils.generateMnemonic();
        return new KeyManager(mnemonic);
    }

    @Override
    public byte[] sign(String stdSignMsg) {
        byte[] signDoc = stdSignMsg.getBytes(StandardCharsets.UTF_8);
        byte[] res = new byte[0];
        try {
            res = SM2Utils.sign(this.getPrivKey(), signDoc);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String export(String password) throws IOException {
        byte[] privKeyTemp = privKey.toByteArray();
        byte[] prefixAmino = getPrefixAmino(PRIV_KEY_NAME);
        byte[] privKeyAmino = ArrayUtils.addAll(prefixAmino, privKeyTemp);

        String salt = BCrypt.gensalt(LOG_ROUNDS);
        String keyHash = BCrypt.hashpw(password, salt);
        byte[] keyHashByte = keyHash.getBytes(StandardCharsets.UTF_8);
        byte[] keyHashSha256 = HashUtils.sha256(keyHashByte);

        SimpleBox box = new SimpleBox(keyHashSha256);
        byte[] encBytes = box.seal(privKeyAmino);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ArmoredOutputStreamImpl aOS = new ArmoredOutputStreamImpl(byteStream);
        String realSaltString = salt.substring(REAL_SALT_BEGIN_POS, REAL_SALT_BEGIN_POS + REAL_SALT_BASE64_LEN);
        byte[] realSaltByte = decode_base64(realSaltString, 16);

        aOS.setHeader("salt", Hex.toHexString(realSaltByte).toUpperCase());
        aOS.setHeader("type", "sm2");
        aOS.setHeader("kdf", "bcrypt");
        aOS.write(encBytes);
        aOS.close();

        return byteStream.toString().trim();
    }

    public byte[] getPrefixAmino(String algoPrivKeyName) {
        byte[] hash = HashUtils.sha256(algoPrivKeyName.getBytes(StandardCharsets.UTF_8));
        int ptr = 0;
        while (hash[ptr] == 0) ptr++;
        ptr += 3;
        while (hash[ptr] == 0) ptr++;
        byte[] prefix = new byte[5];
        System.arraycopy(hash, ptr, prefix, 0, 4);
        prefix[4] = 32;
        return prefix;
    }

    public String getMnemonic() {
        if (StringUtils.isEmpty(mnemonic)) {
            throw new IritaSDKException("this KeyManger can't export mnemonic");
        }

        return mnemonic;
    }

    @Override
    public BigInteger getPrivKey() {
        return privKey;
    }

    @Override
    public String getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "KeyManager{" +
                "privKey='" + privKey + '\'' +
                ", addr='" + addr + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                '}';
    }
}


package iservice.sdk.module.impl;

import com.codahale.xsalsa20poly1305.SimpleBox;
import iservice.sdk.entity.Key;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.util.Bip39Utils;
import iservice.sdk.util.SM2Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.mindrot.jbcrypt.BCrypt;
import org.web3j.crypto.Hash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static iservice.sdk.module.impl.BCryptImpl.decode_base64;

public class SM2KeyServiceImpl extends AbstractKeyServiceImpl {

    public SM2KeyServiceImpl(IKeyDAO keyDAO) {
        super(keyDAO);
    }

    @Override
    public Mnemonic addKey(String name, String password) throws ServiceSDKException {
        String mnemonic = Bip39Utils.generateMnemonic();
        DeterministicKey dk = super.generateDeterministicKey(mnemonic);

        SM2Utils sm2Utils = new SM2Utils();
        ECPoint pubkey = sm2Utils.getPubkeyFromPrivkey(dk.getPrivKey());

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

        SM2Utils sm2Utils = new SM2Utils();
        ECPoint pubkey = sm2Utils.getPubkeyFromPrivkey(dk.getPrivKey());

        byte[] encoded = pubkey.getEncoded(true);
        byte[] hash = Hash.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);
        String addr = super.toBech32(pre20);
        super.saveKey(name, password, addr, dk.getPrivKeyBytes());
        return addr;
    }
    @Override
    public String importFromKeystore(String name, String password, String keystore) {
        throw new Error("The import method is not supported at this time");
//        try {
//            Credentials credentials = WalletUtils.loadJsonCredentials(password, keystore);
//            ECKeyPair keyPair = credentials.getEcKeyPair();
//            BigInteger privKey = keyPair.getPrivateKey();
//
//
//            SM2Utils sm2Utils = new SM2Utils();
//            ECPoint pubkey = sm2Utils.getPubkeyFromPrivkey(dk.getPrivKey());
//
//            byte[] encoded = pubkey.getEncoded(true);
//            byte[] hash = Hash.sha256(encoded);
//            byte[] pre20 = new byte[20];
//            System.arraycopy(hash, 0, pre20, 0, 20);
//            String addr = super.toBech32(pre20);
//            super.saveKey(name, password, addr, Utils.bigIntegerToBytes(privKey, 32));
//            return addr;
//
//        } catch (CipherException e) {
//            e.printStackTrace();
//            throw new ServiceSDKException(e.getMessage(), e);
//        }
    }

    @Override
    public String exportKeystore(String name, String keyPassword, String keystorePassword, File destinationDirectory) throws IOException {

// privKey,eypassworlgo, keystorePassword,destinationDirectory : privKeyArmor + writeInFile
// 1 generate a salt Bytes(uint8)<255. It has 16 number(10); it is number(16) in keystore
// 2 generate a keyvalue

        Key key = super.getKey(name, keyPassword);
        String salt = BCrypt.gensalt(12);
        String keyHash = BCrypt.hashpw(keystorePassword, salt);
        byte[] keyHashByte = keyHash.getBytes(StandardCharsets.UTF_8);
        byte[] keyHashSha256 = Hash.sha256(keyHashByte);

        SimpleBox box = new SimpleBox(keyHashSha256);
        byte[] privKeyTemp = key.getPrivKey();
        String prefix = "27b2937220";
        byte[] prefixByte = Hex.decode(prefix);
        byte[] privKeyAmino = (byte[]) ArrayUtils.addAll(prefixByte,privKeyTemp);
        byte[] encBytes = box.seal(privKeyAmino);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ArmoredOutputStreamImpl obj = new ArmoredOutputStreamImpl(byteStream);

        String real_salt = salt.substring(7, 7+22);
        byte[] saltByte = decode_base64(real_salt, 16);
        obj.setHeader("salt", Hex.toHexString(saltByte).toUpperCase());
        obj.setHeader("kdf","bcrypt");
        obj.setHeader("type","sm2");
        obj.write(encBytes);
        obj.close();
        String fileName = writeArmorToFile(destinationDirectory, key.getAddress(), byteStream.toString().trim());

 //test key.getPrivkey to encBytes
//        String privKey16 = bytes_String16(key.getPrivKey());
//        String key16 = bytes_String16(keyHashSha256);
//        String enc16 = bytes_String16(encBytes);
//        System.out.println(privKey16);
//        System.out.println(key16);
//        System.out.println(enc16);
        return  fileName;
    }
//    public String bytes_String16(byte[] b) {
//        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<b.length;i++) {
//            sb.append(String.format("%02x", b[i]));
//        }
//        return sb.toString();
//    }


}

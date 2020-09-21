package iservice.sdk.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Yelong
 */
public class AESUtils {

    private static final String DEFAULT_ALGO = "AES";
    private static final String DEFAULT_HASH = "SHA-1";
    private static final String DEFAULT_CHARSET = "utf-8";

    private static SecretKeySpec generateSecretKey(String password) {
        SecretKeySpec secretKey = null;
        try {
            byte[] key = password.getBytes(DEFAULT_CHARSET);
            MessageDigest sha = MessageDigest.getInstance(DEFAULT_HASH);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, DEFAULT_ALGO);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // should not happen
            e.printStackTrace();
        }
        return secretKey;
    }

    public static byte[] encrypt(byte[] byteContent, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, AESUtils.generateSecretKey(password));
        return cipher.doFinal(byteContent);
    }

    public static byte[] decrypt(byte[] content, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, AESUtils.generateSecretKey(password));
        return cipher.doFinal(content);
    }

}

package iservice.sdk.util;

import org.apache.commons.lang3.StringUtils;
import org.web3j.utils.Numeric;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author Yelong
 */
public class AESUtils {

    private static final String DEFAULT_ALGO = "AES";
    private static final String DEFAULT_HASH = "SHA-1";
    private static final String DEFAULT_CHARSET = "utf-8";

    private static final SecretKeySpec generateSecretKey(String password) {
        SecretKeySpec secretKey = null;
        try {
            byte[] key = password.getBytes(DEFAULT_CHARSET);
            MessageDigest sha = MessageDigest.getInstance(DEFAULT_HASH);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, DEFAULT_ALGO);
        } catch (NoSuchAlgorithmException e) {
            // should not happen
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // should not happen
            e.printStackTrace();
        }
        return secretKey;
    }

    public static final String encrypt(String content, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGO);
        byte[] byteContent = content.getBytes(DEFAULT_CHARSET);
        cipher.init(Cipher.ENCRYPT_MODE, AESUtils.generateSecretKey(password));
        byte[] result = cipher.doFinal(byteContent);
        return Base64.getEncoder().encodeToString(result);
    }

    public static final String decrypt(String content, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, AESUtils.generateSecretKey(password));
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
        return new String(result);
    }

}

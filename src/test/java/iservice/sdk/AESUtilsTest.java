package iservice.sdk;

import iservice.sdk.util.AESUtils;
import org.junit.Test;

import java.util.Base64;

import static org.junit.Assert.assertEquals;

/**
 * @author Yelong
 */
public class AESUtilsTest {

    private static final String TEXT = "1234567890";
    private static final String ENCRYPTED = "E4Go9qFVObHnHxn3GoKtWw==";

    @Test
    public void test1Encrypt() throws Exception {

        byte[] encrypted = AESUtils.encrypt(TEXT.getBytes(), TEXT);
        assertEquals(ENCRYPTED, Base64.getEncoder().encodeToString(encrypted));
    }

    @Test
    public void test2Decrypt() throws Exception {

        byte[] text = AESUtils.decrypt(Base64.getDecoder().decode(ENCRYPTED), TEXT);
        assertEquals(TEXT, new String(text));
    }

}
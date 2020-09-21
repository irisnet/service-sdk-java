package iservice.sdk;

import iservice.sdk.entity.Mnemonic;
import iservice.sdk.module.IKeyService;
import iservice.sdk.util.AESUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.xml.soap.Text;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yelong
 */
public class AESUtilsTest {

    private static final String TEXT = "1234567890";
    private static final String ENCRYPTED = "E4Go9qFVObHnHxn3GoKtWw==";

    @Test
    public void test1Encrypt() throws Exception {

        String encrypted = AESUtils.encrypt(TEXT, TEXT);
        assertEquals(ENCRYPTED, encrypted);
    }

    @Test
    public void test2Decrypt() throws Exception {

        String text = AESUtils.decrypt(ENCRYPTED, TEXT);
        assertEquals(TEXT, text);
    }

}
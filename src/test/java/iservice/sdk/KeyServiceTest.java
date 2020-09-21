package iservice.sdk;

import iservice.sdk.entity.Mnemonic;
import iservice.sdk.module.IKeyService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yelong
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KeyServiceTest {

    private static IKeyService keyService = ServiceClientBuilder.create().build().getKeyService();
    private static final String HRP = "iaa";
    private static Map<KEYS, Object> paramMap = new HashMap<>();
    private enum KEYS {
        ADDRESS,
        MNEMONIC
    }

    @Test
    public void test1AddKey() throws Exception {

        Mnemonic mnemonic = keyService.addKey("test", "123456");
        assertNotNull(mnemonic.getAddress());
        assertNotNull(mnemonic.getMnemonic());
        assertTrue("Wrong HRP", mnemonic.getAddress().startsWith(HRP));
        assertEquals("Wrong Words Count", 24, mnemonic.getMnemonic().split(" ").length);
        paramMap.put(KEYS.ADDRESS, mnemonic.getAddress());
        paramMap.put(KEYS.MNEMONIC, mnemonic.getMnemonic());
    }

    @Test
    public void test2RecoverKey() throws Exception {
        String address = keyService.recoverKey("test1", "123456", (String) paramMap.get(KEYS.MNEMONIC), true, 0, "");
        assertNotNull(address);
        assertTrue("Wrong HRP", address.startsWith(HRP));
        assertEquals("Wrong Address", paramMap.get(KEYS.ADDRESS), address);
    }

    @Test
    public void test3ShowAddress() throws Exception {
        String address = keyService.showAddress("test1");
        assertNotNull(address);
        assertEquals("Wrong Address", paramMap.get(KEYS.ADDRESS), address);
    }

    @Test
    public void test4DeleteKey() throws Exception {
        keyService.deleteKey("test1", "123456");
    }
}
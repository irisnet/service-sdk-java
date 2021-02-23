package iservice.sdk;

import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.entity.options.ServiceClientOptions;
import iservice.sdk.module.IKeyService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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

    private ServiceClientOptions options = new ServiceClientOptions();
    private static IKeyService keyService;
    private static final String HRP = "iaa";
    private static Map<KEYS, Object> paramMap = new HashMap<>();

    public KeyServiceTest() throws URISyntaxException {
        options.setGrpcURI((new URI("http://localhost:9090")));
        keyService = ServiceClientFactory.getInstance().setOptions(options).getClient().getKeyService();
    }

    private enum KEYS {
        ADDRESS,
        MNEMONIC,
        FILEPATH
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

    @Test
    public void test5ExportKey() throws Exception {
        String fileName = keyService.exportKeystore("test", "123456", "123456", new File("/tmp/"));
        assertNotNull(fileName);
        paramMap.put(KEYS.FILEPATH, "/tmp/" + fileName);
    }

    @Test
    public void test6ImporttKey() throws Exception {
        String address = keyService.importFromKeystore("test1", "123456", "123456", (String) paramMap.get(KEYS.FILEPATH));
        assertNotNull(address);
        assertEquals("Wrong Address", paramMap.get(KEYS.ADDRESS), address);
    }
}
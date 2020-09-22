package iservice.sdk;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Mnemonic;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.module.IKeyService;
import org.web3j.utils.Numeric;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        IKeyService keyService = ServiceClientFactory.getInstance().setOptions(new ServiceClientOptions()).getClient().getKeyService();
        String address = keyService.recoverKey("test", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");
        System.out.println("Address: " + address);

        String hex = Numeric.toHexString("test".getBytes());
        String sig = keyService.signTx(hex, "test", "123456", false);
        System.out.println(sig);

    }
}

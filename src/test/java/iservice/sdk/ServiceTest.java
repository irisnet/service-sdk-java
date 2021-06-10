package iservice.sdk;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.options.ServiceClientOptions;
import org.bouncycastle.crypto.CryptoException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Yelong
 */
public class ServiceTest {
    public static void main(String[] args) throws URISyntaxException, IOException, CryptoException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setGrpcURI(new URI("http://localhost:9090"));
        options.setRpcURI(new URI("http://localhost:26657"));
        ServiceClient client = ServiceClientFactory.getInstance().setOptions(options).getClient();
        client.getKeyService().recoverKey("consumer", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");
        client.getKeyService().recoverKey("provider", "123456", "help such mushroom spell cream cattle cute brush crucial boat flat system oxygen apart sock leave position jaguar winner violin manage exchange scissors employ", true, 0, "");
        client.callService(new TestServiceRequest());
    }
}

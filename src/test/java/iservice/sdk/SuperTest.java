package iservice.sdk;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.options.ServiceClientOptions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author : ori
 * @date : 2020/9/24 8:20 下午
 */
public class SuperTest {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setRpcURI(new URI("http://10.1.4.99:26657"));
        options.setGrpcURI(new URI("http://10.1.4.99:9090"));
        options.setRpcStartTimeout(TimeUnit.SECONDS.toMillis(7));
        ServiceClient client = ServiceClientFactory.getInstance()
                .setOptions(options)
                .addListener(new TestConsumerListener())
                .addListener(new TestProviderListener())
                .getClient();
        client.getKeyService().recoverKey("consumer", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");
        client.getKeyService().recoverKey("provider", "123456", "help such mushroom spell cream cattle cute brush crucial boat flat system oxygen apart sock leave position jaguar winner violin manage exchange scissors employ", true, 0, "");
        client.start();
    }
}

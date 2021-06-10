package iservice.sdk;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.SignAlgo;
import iservice.sdk.entity.options.ServiceClientOptions;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author : ori
 * @date : 2020/9/24 8:20 下午
 */
public class SuperTest {

    public static void main(String[] args) throws URISyntaxException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setRpcURI(new URI("http://localhost:26657"));
        options.setGrpcURI(new URI("http://localhost:9090"));
        options.setRpcStartTimeout(TimeUnit.SECONDS.toMillis(7));
        ServiceClient client = ServiceClientFactory.getInstance()
                .setOptions(options)
                .addListener(new TestConsumerListener())
                .addListener(new TestProviderListener())
                .getClient();

        client.getKeyService().recoverKey("provider", "123456", "know pioneer position grape marine angle giggle access cement castle keep base minimum spoil nice excite hurdle embody police please frame fancy ritual limit", true, 0, "");
        client.getKeyService().recoverKey("consumer", "123456", "initial erosion post trouble cabin gas purity carry system satoshi dry certain invest spatial select ostrich hour inside okay vivid stumble crawl avoid want", true, 0, "");
        client.start();
    }
}

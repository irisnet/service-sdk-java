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
        options.setSignAlgo(SignAlgo.SM2);
        options.setRpcURI(new URI("http://localhost:26657"));
        options.setGrpcURI(new URI("http://localhost:9090"));
        options.setRpcStartTimeout(TimeUnit.SECONDS.toMillis(7));
        ServiceClient client = ServiceClientFactory.getInstance()
                .setOptions(options)
                .addListener(new TestConsumerListener())
                .addListener(new TestProviderListener())
                .getClient();

        String addr = client.getKeyService().recoverKey("consumer", "123456", "liquid addict dinosaur tumble model air mansion juice purpose inherit discover project solve apology crew crack engage dawn miss foam achieve broom tenant minute", true, 0, "");
        System.out.printf(addr);
        client.getKeyService().recoverKey("provider", "123456", "learn inmate snake demise fall curtain wire ability quick modify reduce find casual man aerobic there order magnet leaf cloud lab super once lava", true, 0, "");
        client.start();
    }
}

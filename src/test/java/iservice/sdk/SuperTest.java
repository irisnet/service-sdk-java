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
        options.setRpcURI(new URI("http://172.18.68.137:26657"));
        options.setGrpcURI(new URI("http://172.18.68.137:9090"));
        options.setRpcStartTimeout(TimeUnit.SECONDS.toMillis(7));
        ServiceClient client = ServiceClientFactory.getInstance()
                .setOptions(options)
                .addListener(new TestConsumerListener())
                .addListener(new TestProviderListener())
                .getClient();
        client.getKeyService().recoverKey("consumer", "123456", "picnic tuna easy desert wall proof call scorpion town doctor agree domain system wealth aspect venue course pizza truck settle taxi merge easy drill", true, 0, "");
        client.getKeyService().recoverKey("provider", "123456", "wash bargain vicious basket blur assist fault august involve quit fit camp eagle supreme chef process auction surge crucial orphan ticket hundred express bike", true, 0, "");
        client.start();
    }
}

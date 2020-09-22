package iservice.sdk;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) throws Exception{
        ServiceClientOptions options = new ServiceClientOptions();
        URI websocketURI = new URI("ws://localhost:8083/websocket/?request=e2lkOjE7cmlkOjI2O3Rva2VuOiI0MzYwNjgxMWM3MzA1Y2NjNmFiYjJiZTExNjU3OWJmZCJ9");
        options.setUri(websocketURI);
        ServiceClient sc = ServiceClientFactory.getInstance()
                .setOptions(options)
                .addListener(new TestConsumerListener())
                .addListener(new TestProviderListener())
                .getClient();
        new Thread(sc::startWebSocketClient).start();

        TestRequest req = new TestRequest();
        req.setS1("req1");
        req.setS2("req2");
        System.out.println("----------------- Consumer -----------------");
    }
}

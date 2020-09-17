package iservice.sdk;

import iservice.sdk.impl.ServiceClient;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) {
        ServiceClient sc = ServiceClientBuilder.create().addListener(new TestConsumerListener()).addListener(new TestProviderListener()).build();
        TestRequest req = new TestRequest();
        req.setS1("req1");
        req.setS2("req2");
        System.out.println("----------------- Consumer -----------------");
        sc.callService(req);
    }
}

package iservice.sdk;

import iservice.sdk.TestRequest;
import iservice.sdk.TestResponse;
import iservice.sdk.entity.ServiceListenerOptions;
import iservice.sdk.impl.AbstractProviderListener;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestProviderListener extends AbstractProviderListener<TestRequest, TestResponse> {

    @Override
    public ServiceListenerOptions getOptions() {

        ServiceListenerOptions options = new ServiceListenerOptions();
        return options;
    }

    @Override
    public TestResponse onRequest(TestRequest req) {
        System.out.println("----------------- Provider -----------------");
        System.out.println("Got request");
        System.out.println(req.toString());
        TestResponse res = new TestResponse();
        res.setS1("res1");
        res.setS2("res2");
        System.out.println("Sending response");

        return res;
    }
}

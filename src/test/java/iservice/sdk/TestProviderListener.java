package iservice.sdk;

import iservice.sdk.core.AbstractProviderListener;
import iservice.sdk.entity.options.ProviderListenerOptions;
import iservice.sdk.message.ServiceReqMessage;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestProviderListener extends AbstractProviderListener<TestServiceRequest, TestResponse> {

    @Override
    public ProviderListenerOptions getOptions() {
        ProviderListenerOptions options = new ProviderListenerOptions();
        options.setServiceName("test");
        options.setAddress("iaa19edqjunszsu49uw04f4y6sfknw553cfsuzsjc5");
        return options;
    }

    @Override
    protected Class<TestServiceRequest> getReqClass() {
        return TestServiceRequest.class;
    }

    @Override
    protected boolean checkValidate(TestServiceRequest res) {
        return res != null;
    }

    @Override
    public TestResponse onRequest(TestServiceRequest req) {
        System.out.println("----------------- Provider -----------------");
        System.out.println("Got request");
        System.out.println(req.toString());
        TestResponse res = new TestResponse();
        System.out.println("Sending response");
        return res;
    }


}

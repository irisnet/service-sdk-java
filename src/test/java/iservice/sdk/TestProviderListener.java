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
        options.setAddress("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht");
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

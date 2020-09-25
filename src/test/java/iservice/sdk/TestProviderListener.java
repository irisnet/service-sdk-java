package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.core.AbstractProviderListener;
import iservice.sdk.entity.options.ProviderListenerOptions;

/**
 * @author Yelong
 */
public class TestProviderListener extends AbstractProviderListener<TestServiceRequest.TestInput, TestServiceResponse.TestOutput, TestServiceResponse> {

    @Override
    public ProviderListenerOptions getOptions() {
        ProviderListenerOptions options = new ProviderListenerOptions();
        options.setServiceName("test");
        options.setAddress("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht");
        return options;
    }

    @Override
    protected Class<TestServiceRequest.TestInput> getReqClass() {
        return TestServiceRequest.TestInput.class;
    }

    @Override
    public TestServiceResponse onRequest(TestServiceRequest.TestInput req) {
        System.out.println("----------------- Provider -----------------");
        System.out.println("Got request");
        System.out.println(JSON.toJSONString(req));
        TestServiceResponse.TestOutput output = new TestServiceResponse().new TestOutput("TestType", "TestData");
        System.out.println("Sending response");
        TestServiceResponse res = new TestServiceResponse();
        res.setBody(output);
        return res;
    }
}

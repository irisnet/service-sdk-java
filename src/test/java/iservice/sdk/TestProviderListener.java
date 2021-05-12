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
        options.setServiceName("update");
        options.setAddress("iaa1mxatp6h7huukny9uha8u45qkfas2f6lgpa4nhw");
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

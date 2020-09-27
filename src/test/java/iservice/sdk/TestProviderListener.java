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
        options.setServiceName("credit_file_query_svc");
        options.setAddress("iaa1dwlfeqh5x4982fxmdy0wzsparle7lahljm6cf7");
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

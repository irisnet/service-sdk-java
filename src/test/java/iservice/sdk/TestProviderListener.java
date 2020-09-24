package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.core.AbstractProviderListener;
import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestProviderListener extends AbstractProviderListener<TestServiceRequest, TestResponse> {

    @Override
    public ServiceListenerOptions getOptions() {

        ServiceListenerOptions options = new ServiceListenerOptions();
        return options;
    }

    @Override
    protected TestServiceRequest getReqFromJson(String json) {
        return JSON.parseObject(json, TestServiceRequest.class);
    }

    @Override
    protected boolean checkValidate(TestServiceRequest res) {
        return true;
    }

    @Override
    public TestResponse onRequest(TestServiceRequest req) {
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

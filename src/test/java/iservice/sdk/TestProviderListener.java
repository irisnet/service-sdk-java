package iservice.sdk;

import com.alibaba.fastjson.JSON;
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
    protected TestRequest getReqFromJson(String json) {
        return JSON.parseObject(json,TestRequest.class);
    }

    @Override
    protected boolean checkValidate(TestRequest res) {
        return true;
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

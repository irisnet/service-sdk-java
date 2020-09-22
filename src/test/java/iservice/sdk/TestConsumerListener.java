package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.core.AbstractConsumerListener;
import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestConsumerListener extends AbstractConsumerListener<TestResponse> {

    @Override
    public ServiceListenerOptions getOptions() {

        ServiceListenerOptions options = new ServiceListenerOptions();
        return options;
    }

    @Override
    protected TestResponse getReqFromJson(String json) {
        return JSON.parseObject(json, TestResponse.class);
    }

    @Override
    public void onResponse(TestResponse res) {

        System.out.println("----------------- Consumer -----------------");
        System.out.println("Got response");
        System.out.println(res.toString());
    }

    @Override
    protected boolean checkValidate(TestResponse res) {
        return true;
    }
}

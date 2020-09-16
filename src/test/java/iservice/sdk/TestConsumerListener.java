package iservice.sdk;

import iservice.sdk.entity.ServiceListenerOptions;
import iservice.sdk.impl.AbstractConsumerListener;

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
    public void onResponse(TestResponse res) {

        System.out.println("----------------- Consumer -----------------");
        System.out.println("Got response");
        System.out.println(res.toString());
    }
}

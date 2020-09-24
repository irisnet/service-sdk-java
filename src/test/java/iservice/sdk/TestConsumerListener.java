package iservice.sdk;

import iservice.sdk.core.AbstractConsumerListener;
import iservice.sdk.entity.options.ConsumerListenerOptions;
import iservice.sdk.message.ServiceReqMessage;

/**
 * Created by mitch on 2020/9/16.
 */
public class TestConsumerListener extends AbstractConsumerListener<ServiceReqMessage> {

    @Override
    public ConsumerListenerOptions getOptions() {
        ConsumerListenerOptions options = new ConsumerListenerOptions();
        options.setAddress("iaa1ve5p4xas7ptmp367mkvlka86zszpt93y8h78yd");
        options.setModule("service");
        options.setSender("iaa19edqjunszsu49uw04f4y6sfknw553cfsuzsjc5");
        return options;
    }

    @Override
    protected Class<ServiceReqMessage> getReqClass() {
        return ServiceReqMessage.class;
    }

    @Override
    public void onResponse(ServiceReqMessage res) {

        System.out.println("----------------- Consumer start -----------------");
        System.out.println("do response");
        System.out.println("----------------- Consumer end -----------------");
    }

    @Override
    protected boolean checkValidate(ServiceReqMessage res) {
        return res!= null;
    }
}

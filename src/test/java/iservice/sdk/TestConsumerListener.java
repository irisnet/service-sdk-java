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
        options.setAddress("iaa176l662tt6e3uqxu57hdxpupk972gw0y8j4aa0a");
        options.setModule("service");
        options.setSender("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht");
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

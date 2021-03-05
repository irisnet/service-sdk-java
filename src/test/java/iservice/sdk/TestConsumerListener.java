package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.core.AbstractConsumerListener;
import iservice.sdk.entity.options.ConsumerListenerOptions;

/**
 * @author Yelong
 */
public class TestConsumerListener extends AbstractConsumerListener<TestServiceResponse.TestOutput> {

    @Override
    public ConsumerListenerOptions getOptions() {
        ConsumerListenerOptions options = new ConsumerListenerOptions();
        options.setAddress("iaa1xnddejhrcvzlhpkp45jzxlug4m82mglaay53w0");
        options.setRequestContextID("50A59F536789F785D0DF48EB81BBADBFE2C1F4C02A75AF22D4545EE5D8D9870C0000000000000000");
        options.setServiceName("update");
        return options;
    }

    @Override
    protected Class<TestServiceResponse.TestOutput> getReqClass() {
        return TestServiceResponse.TestOutput.class;
    }

    @Override
    public void onResponse(TestServiceResponse.TestOutput res) {
        System.out.println("----------------- Consumer -----------------");
        System.out.println("Got response: "+ JSON.toJSONString(res));
    }

}

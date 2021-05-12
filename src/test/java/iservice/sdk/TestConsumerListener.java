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
        options.setSender("iaa1mxatp6h7huukny9uha8u45qkfas2f6lgpa4nhw");
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

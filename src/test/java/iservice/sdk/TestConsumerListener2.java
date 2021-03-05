package iservice.sdk;

import com.alibaba.fastjson.JSON;
import iservice.sdk.core.AbstractConsumerListener;
import iservice.sdk.entity.options.ConsumerListenerOptions;

/**
 * @author Yelong
 */
public class TestConsumerListener2 extends AbstractConsumerListener<TestServiceResponse.TestOutput> {

    @Override
    public ConsumerListenerOptions getOptions() {
        ConsumerListenerOptions options = new ConsumerListenerOptions();
        options.setAddress("iaa1vrh28848qh5sl4ryfvl42nv6jd6twn2fz85hm7");
        options.setRequestContextID("50A59F536789F785D0DF48EB81BBADBFE2C1F4C02A75AF22D4545EE5D8D9870C0000000000000000");
        options.setServiceName("cross_region_open_account_query_svc");
        return options;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new TestConsumerListener2().getOptions()));
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

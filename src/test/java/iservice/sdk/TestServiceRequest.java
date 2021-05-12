package iservice.sdk;

import com.google.common.collect.Lists;
import cosmos.base.v1beta1.CoinOuterClass;
import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.Header;

import java.util.List;

/**
 * @author Yelong
 */
public class TestServiceRequest extends BaseServiceRequest<TestServiceRequest.TestInput> {

    public TestServiceRequest() {
    }

    @Override
    public String getKeyName() {
        return "consumer";
    }

    @Override
    public String getKeyPassword() {
        return "123456";
    }

    @Override
    public String getServiceName() {
        return "credit_file_query_svc";
    }

    @Override
    public List<String> getProviders() {

        return Lists.newArrayList("iaa1dwlfeqh5x4982fxmdy0wzsparle7lahljm6cf7");
    }

    @Override
    public List<CoinOuterClass.Coin> getServiceFeeCap() {
        return Lists.newArrayList(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("point").build());
    }

    @Override
    public Header getHeader() {
        return new Header();
    }

    @Override
    public TestInput getBody() {
        return new TestInput("1", "credit_file_query_svc", "data");
    }

    public class TestInput {

        private String id;
        private String name;
        private String data;

        public TestInput(String id, String name, String data) {
            this.id = id;
            this.name = name;
            this.data = data;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

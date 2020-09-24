package iservice.sdk;

import cosmos.base.v1beta1.CoinOuterClass;
import iservice.sdk.entity.BaseServiceRequest;

import java.util.List;

/**
 * @author Yelong
 */
public class TestServiceRequest extends BaseServiceRequest<TestServiceRequest.TestInput> {


    @Override
    public String getKeyName() {
        return null;
    }

    @Override
    public String getKeyPassword() {
        return null;
    }

    @Override
    public String getServiceName() {
        return null;
    }

    @Override
    public List<String> getProviders() {
        return null;
    }

    @Override
    public List<CoinOuterClass.Coin> getServiceFeeCap() {
        return null;
    }

    @Override
    public TestInput getRequest() {
        return null;
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

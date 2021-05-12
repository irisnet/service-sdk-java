package iservice.sdk;

import iservice.sdk.entity.BaseServiceResponse;
import iservice.sdk.entity.Header;

/**
 * @author Yelong
 */
public class TestServiceResponse extends BaseServiceResponse<TestServiceResponse.TestOutput> {

    private TestServiceResponse.TestOutput body;

    public TestServiceResponse() {
    }

    @Override
    public String getKeyName() {
        return "provider";
    }

    @Override
    public String getKeyPassword() {
        return "123456";
    }

    @Override
    public String getServiceName() {
        return "test";
    }

    @Override
    public Header getHeader() {
        return new Header();
    }

    @Override
    public TestOutput getBody() {
        return this.body;
    }

    public void setBody(TestOutput body) {
        this.body = body;
    }

    public class TestOutput {
        private String type;
        private String data;

        public TestOutput(String type, String data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

}

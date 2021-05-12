package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;

public class ValueServiceRespond {
    @JSONField(name = "request_id")
    private String requestId;
    private String provider;
    private String result;
    private String output;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

}
package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;

public class Response {
    private String provider;
    private String consumer;
    private String result;
    private String output;
    @JSONField(name = "request_context_id")
    private String requestContextId;
    @JSONField(name = "request_context_batch_counter")
    private String requestContextBatchCounter;

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getConsumer() {
        return consumer;
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

    public void setRequestContextId(String requestContextId) {
        this.requestContextId = requestContextId;
    }

    public String getRequestContextId() {
        return requestContextId;
    }

    public void setRequestContextBatchCounter(String requestContextBatchCounter) {
        this.requestContextBatchCounter = requestContextBatchCounter;
    }

    public String getRequestContextBatchCounter() {
        return requestContextBatchCounter;
    }
}
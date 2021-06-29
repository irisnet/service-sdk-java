package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RequestByContext {
    private String id;
    @JSONField(name = "service_name")
    private String serviceName;
    private String provider;
    private String consumer;
    private String input;
    @JSONField(name = "service_fee")
    private List<String> serviceFee;
    @JSONField(name = "request_height")
    private String requestHeight;
    @JSONField(name = "expiration_height")
    private String expirationHeight;
    @JSONField(name = "request_context_id")
    private String requestContextId;
    @JSONField(name = "request_context_batch_counter")
    private String requestContextBatchCounter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<String> getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(List<String> serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getRequestHeight() {
        return requestHeight;
    }

    public void setRequestHeight(String requestHeight) {
        this.requestHeight = requestHeight;
    }

    public String getExpirationHeight() {
        return expirationHeight;
    }

    public void setExpirationHeight(String expirationHeight) {
        this.expirationHeight = expirationHeight;
    }

    public String getRequestContextId() {
        return requestContextId;
    }

    public void setRequestContextId(String requestContextId) {
        this.requestContextId = requestContextId;
    }

    public String getRequestContextBatchCounter() {
        return requestContextBatchCounter;
    }

    public void setRequestContextBatchCounter(String requestContextBatchCounter) {
        this.requestContextBatchCounter = requestContextBatchCounter;
    }
}
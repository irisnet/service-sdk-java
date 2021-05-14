package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.Coin;

import java.util.List;

public class Request {
    private String id;
    @JSONField(name = "service_name")
    private String serviceName;
    private String provider;
    private String consumer;
    private String input;
    @JSONField(name = "service_fee")
    private List<Coin> serviceFee;
    @JSONField(name = "super_mode")
    private boolean superMode;
    @JSONField(name = "request_height")
    private String requestHeight;
    @JSONField(name = "expiration_height")
    private String expirationHeight;
    @JSONField(name = "request_context_id")
    private String requestContextId;
    @JSONField(name = "request_context_batch_counter")
    private String requestContextBatchCounter;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

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

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setServiceFee(List<Coin> serviceFee) {
        this.serviceFee = serviceFee;
    }

    public List<Coin> getServiceFee() {
        return serviceFee;
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public boolean getSuperMode() {
        return superMode;
    }

    public void setRequestHeight(String requestHeight) {
        this.requestHeight = requestHeight;
    }

    public String getRequestHeight() {
        return requestHeight;
    }

    public void setExpirationHeight(String expirationHeight) {
        this.expirationHeight = expirationHeight;
    }

    public String getExpirationHeight() {
        return expirationHeight;
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
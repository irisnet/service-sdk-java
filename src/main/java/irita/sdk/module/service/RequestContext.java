package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.Coin;

import java.util.List;

public class RequestContext {
    @JSONField(name = "service_name")
    private String serviceName;
    private List<String> providers;
    private String consumer;
    private String input;
    @JSONField(name = "service_fee_cap")
    private List<Coin> serviceFeeCap;
    @JSONField(name = "module_name")
    private String moduleName;
    private String timeout;
    @JSONField(name = "super_mode")
    private boolean superMode;
    private boolean repeated;
    @JSONField(name = "repeated_frequency")
    private String repeatedFrequency;
    @JSONField(name = "repeated_total")
    private String repeatedTotal;
    @JSONField(name = "batch_counter")
    private String batchCounter;
    @JSONField(name = "batch_request_count")
    private int batchRequestCount;
    @JSONField(name = "batch_response_count")
    private int batchResponseCount;
    @JSONField(name = "batch_response_threshold")
    private int batchResponseThreshold;
    @JSONField(name = "response_threshold")
    private int responseThreshold;
    @JSONField(name = "batch_state")
    private String batchState;
    private String state;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
    }

    public List<String> getProviders() {
        return providers;
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

    public void setServiceFeeCap(List<Coin> serviceFeeCap) {
        this.serviceFeeCap = serviceFeeCap;
    }

    public List<Coin> getServiceFeeCap() {
        return serviceFeeCap;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setSuperMode(boolean superMode) {
        this.superMode = superMode;
    }

    public boolean getSuperMode() {
        return superMode;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean getRepeated() {
        return repeated;
    }

    public void setRepeatedFrequency(String repeatedFrequency) {
        this.repeatedFrequency = repeatedFrequency;
    }

    public String getRepeatedFrequency() {
        return repeatedFrequency;
    }

    public void setRepeatedTotal(String repeatedTotal) {
        this.repeatedTotal = repeatedTotal;
    }

    public String getRepeatedTotal() {
        return repeatedTotal;
    }

    public void setBatchCounter(String batchCounter) {
        this.batchCounter = batchCounter;
    }

    public String getBatchCounter() {
        return batchCounter;
    }

    public void setBatchRequestCount(int batchRequestCount) {
        this.batchRequestCount = batchRequestCount;
    }

    public int getBatchRequestCount() {
        return batchRequestCount;
    }

    public void setBatchResponseCount(int batchResponseCount) {
        this.batchResponseCount = batchResponseCount;
    }

    public int getBatchResponseCount() {
        return batchResponseCount;
    }

    public void setBatchResponseThreshold(int batchResponseThreshold) {
        this.batchResponseThreshold = batchResponseThreshold;
    }

    public int getBatchResponseThreshold() {
        return batchResponseThreshold;
    }

    public void setResponseThreshold(int responseThreshold) {
        this.responseThreshold = responseThreshold;
    }

    public int getResponseThreshold() {
        return responseThreshold;
    }

    public void setBatchState(String batchState) {
        this.batchState = batchState;
    }

    public String getBatchState() {
        return batchState;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.Coin;

import java.util.List;

public class ValueServiceRequest {
    @JSONField(name = "service_name")
    private String serviceName;
    private List<String> providers;
    private String consumer;
    private String input;
    @JSONField(name = "service_fee_cap")
    private List<Coin> serviceFeeCap;
    private String timeout;
    private boolean repeated;
    @JSONField(name = "repeated_total")
    private String repeatedTotal;
    @JSONField(name = "req_context_id")
    private String reqContextId;
    @JSONField(name = "req_id")
    private String reqId;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(List<String> providers) {
        this.providers = providers;
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

    public List<Coin> getServiceFeeCap() {
        return serviceFeeCap;
    }

    public void setServiceFeeCap(List<Coin> serviceFeeCap) {
        this.serviceFeeCap = serviceFeeCap;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public String getRepeatedTotal() {
        return repeatedTotal;
    }

    public void setRepeatedTotal(String repeatedTotal) {
        this.repeatedTotal = repeatedTotal;
    }

    public String getReqContextId() {
        return reqContextId;
    }

    public void setReqContextId(String reqContextId) {
        this.reqContextId = reqContextId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
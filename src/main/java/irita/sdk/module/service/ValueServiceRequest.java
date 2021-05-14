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

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean getRepeated() {
        return repeated;
    }

    public void setRepeatedTotal(String repeatedTotal) {
        this.repeatedTotal = repeatedTotal;
    }

    public String getRepeatedTotal() {
        return repeatedTotal;
    }
}
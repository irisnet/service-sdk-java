package irita.sdk.module.service;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.Coin;

import java.util.List;

public class ServiceBinding {
    @JSONField(name = "service_name")
    private String serviceName;
    private String provider;
    private List<Coin> deposit;
    private String pricing;
    private String qos;
    private String options;
    private boolean available;
    @JSONField(name = "disabled_time")
    private String disabledTime;
    private String owner;

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

    public void setDeposit(List<Coin> deposit) {
        this.deposit = deposit;
    }

    public List<Coin> getDeposit() {
        return deposit;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getPricing() {
        return pricing;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }

    public String getQos() {
        return qos;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setDisabledTime(String disabledTime) {
        this.disabledTime = disabledTime;
    }

    public String getDisabledTime() {
        return disabledTime;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
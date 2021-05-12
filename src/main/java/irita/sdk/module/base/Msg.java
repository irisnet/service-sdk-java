package irita.sdk.module.base;

import irita.sdk.module.service.ValueServiceRequest;

public class Msg {
    private String type;
    private ValueServiceRequest value;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setValue(ValueServiceRequest value) {
        this.value = value;
    }

    public ValueServiceRequest getValue() {
        return value;
    }
}
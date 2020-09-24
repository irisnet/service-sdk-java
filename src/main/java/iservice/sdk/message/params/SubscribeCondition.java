package iservice.sdk.message.params;

import iservice.sdk.enums.SubscribeQueryKeyEnum;

/**
 * @author : ori
 * @date : 2020/9/23 6:20 下午
 */
public class SubscribeCondition {

    private SubscribeQueryKeyEnum key;
    private ConditionOpType op;
    private String value;

    public SubscribeCondition(SubscribeQueryKeyEnum key) {
        this.key = key;
    }

    public SubscribeCondition eq(String value) {
        this.op = ConditionOpType.EQUALS;
        this.value = value;
        return this;
    }

    public SubscribeCondition contains(String value) {
        this.op = ConditionOpType.CONTAINS;
        this.value = value;
        return this;
    }

    public String getConditionString(){
        return key.getKey() + op.getOp() + "'"+value+"'";
    }

    public SubscribeQueryKeyEnum getKey() {
        return key;
    }

    public void setKey(SubscribeQueryKeyEnum key) {
        this.key = key;
    }

    public ConditionOpType getOp() {
        return op;
    }

    public void setOp(ConditionOpType op) {
        this.op = op;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SubscribeCondition{" +
                "key='" + key + '\'' +
                ", op=" + op +
                ", value='" + value + '\'' +
                '}';
    }
}

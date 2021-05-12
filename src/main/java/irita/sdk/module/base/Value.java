package irita.sdk.module.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Value {
    private List<Msg> msg;
    private Coin fee;
    private List<String> signatures;
    private String memo;
    @JSONField(name = "timeout_height")
    private String timeoutHeight;

    public void setMsg(List<Msg> msg) {
        this.msg = msg;
    }

    public List<Msg> getMsg() {
        return msg;
    }

    public void setFee(Coin fee) {
        this.fee = fee;
    }

    public Coin getFee() {
        return fee;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setTimeoutHeight(String timeoutHeight) {
        this.timeoutHeight = timeoutHeight;
    }

    public String getTimeoutHeight() {
        return timeoutHeight;
    }
}
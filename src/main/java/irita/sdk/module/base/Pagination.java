package irita.sdk.module.base;

import com.alibaba.fastjson.annotation.JSONField;

public class Pagination {
    @JSONField(name = "next_key")
    private String nextKey;
    private String total;

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }
}
package iservice.sdk.entity.ctx;

import iservice.sdk.entity.Header;

import java.util.Arrays;

/**
 * @author : ori
 * @date : 2020/9/29 10:48 上午
 */
public class DataChainContext {
    Header header;
    byte[] obj;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getObj() {
        return obj;
    }

    public void setObj(byte[] obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "DataChainContext{" +
                "header=" + header +
                ", obj=" + Arrays.toString(obj) +
                '}';
    }
}

package iservice.sdk.message;

import iservice.sdk.enums.RpcMethod;
import iservice.sdk.message.params.BaseParam;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author : ori
 * @date : 2020/9/21 7:26 下午
 */
public class WrappedMessage<T extends BaseParam> implements Serializable {
    private final String jsonrpc = "2.0";
    private String id;
    private String method;
    private T params;

    public WrappedMessage() {
    }

    public WrappedMessage(RpcMethod rpcMethod, T params) {
        this.params = params;
        this.method = rpcMethod.getMethod();
        this.id = rpcMethod.getMethod() + "-" + UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    @Override
    public String toString() {
        return "WrappedMessage{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                '}';
    }
}

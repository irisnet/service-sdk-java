package iservice.sdk.entity;

import java.io.Serializable;

/**
 * @author : ori
 * @date : 2020/9/21 7:26 下午
 */
public class WrappedRequest<T> implements Serializable {
    private static final long serialVersionUID = 4739712926654477976L;
    private String jsonrpc = "2.0";
    private String id;
    private String method;
    private T params;

    public WrappedRequest() {
    }

    public WrappedRequest(T params) {
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
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

    @Override
    public String toString() {
        return "WrappedRequest{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                '}';
    }
}

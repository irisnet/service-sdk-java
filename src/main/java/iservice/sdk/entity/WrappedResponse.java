package iservice.sdk.entity;

import java.io.Serializable;

/**
 * @author : ori
 * @date : 2020/9/21 7:26 下午
 */
public class WrappedResponse<T> implements Serializable {
    private static final long serialVersionUID = -3117802482134801657L;
    private String jsonrpc = "2.0";
    private String id;
    private T result;

    public WrappedResponse() {
    }

    public WrappedResponse(T result) {
        this.result = result;
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WrappedResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}

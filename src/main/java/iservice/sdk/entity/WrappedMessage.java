package iservice.sdk.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/21 7:26 下午
 */
public class WrappedMessage<T> implements Serializable {
    String id;
    String method;
    String query;
    T data;

    public WrappedMessage() {
    }

    public WrappedMessage(T data) {
        this.data = data;
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WrappedMessage{" +
                "id='" + id + '\'' +
                ", method='" + method + '\'' +
                ", query='" + query + '\'' +
                ", data=" + data +
                '}';
    }
}

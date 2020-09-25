package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/24 4:45 下午
 */
public class ServiceBaseMessage {
    private String jsonrpc;
    private WebSocketErrorMessage error;
    private String id;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public WebSocketErrorMessage getError() {
        return error;
    }

    public void setError(WebSocketErrorMessage error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseServiceMessage{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", error=" + error +
                ", id='" + id + '\'' +
                '}';
    }
}

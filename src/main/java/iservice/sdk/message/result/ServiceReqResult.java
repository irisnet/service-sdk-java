package iservice.sdk.message.result;

/**
 * @author : ori
 * @date : 2020/9/23 10:27 下午
 */
public class ServiceReqResult {
    private String query;

    private WebSocketResponseResultData<WebSocketResponseResultDataBlockInfo> data;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public WebSocketResponseResultData<WebSocketResponseResultDataBlockInfo> getData() {
        return data;
    }

    public void setData(WebSocketResponseResultData<WebSocketResponseResultDataBlockInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WebSocketResponseResult{" +
                "query='" + query + '\'' +
                ", data=" + data +
                '}';
    }
}

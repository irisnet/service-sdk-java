package iservice.sdk.message;

/**
 * @author : ori
 * @date : 2020/9/25 9:46 上午
 */
public class BaseServiceResult<T> {
    private String query;
    private WebSocketResponseResultData<T> data;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public WebSocketResponseResultData<T> getData() {
        return data;
    }

    public void setData(WebSocketResponseResultData<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseServiceResult{" +
                "query='" + query + '\'' +
                ", data=" + data +
                '}';
    }
}

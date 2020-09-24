package iservice.sdk.message;

import iservice.sdk.message.result.WebSocketResponseResultData;

import java.util.Map;

/**
 * @author : ori
 * @date : 2020/9/24 4:47 下午
 */
public class ServiceResResult {
    private String query;
    private WebSocketResponseResultData<Map<String,TxResult>> data;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public WebSocketResponseResultData<Map<String,TxResult>> getData() {
        return data;
    }

    public void setData(WebSocketResponseResultData<Map<String,TxResult>> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ServiceResResult{" +
                "query='" + query + '\'' +
                ", data=" + data +
                '}';
    }
}

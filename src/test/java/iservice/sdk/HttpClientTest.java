package iservice.sdk;

import iservice.sdk.net.HttpClient;

/**
 * @author : ori
 * @date : 2020/9/25 11:48 上午
 */
public class HttpClientTest {

    public static void main(String[] args) {
        System.out.println(HttpClient.getInstance().get("http://www.baidu.com",null));
        System.out.println(HttpClient.getInstance().post("http://localhost:26657","{\"id\":\"service_client\",\"jsonrpc\":\"2.0\",\"method\":\"broadcast_tx_sync\",\"params\":{\"tx\":\"CogCCoUCCiIvaXJpc21vZC5zZXJ2aWNlLk1zZ1Jlc3BvbmRTZXJ2aWNlEt4BCjpE28xAVnijSQqL11kDRY6j9PZvxT3ddznKD0APSGEbZQAAAAAAAAAAAAAAAAAAAAEAAAAAAABhQAAAEhTLste2CoEuyoJ6TK5ldZvr5KgHTRojeyJjb2RlIjogMjAwLCAibWVzc2FnZSI6ICJzdWNjZXNzIn0iZXsiYm9keSI6eyJkYXRhIjoiVGVzdERhdGEiLCJ0eXBlIjoiVGVzdFR5cGUifSwiaGVhZGVyIjp7ImxvY2F0aW9uIjp7InN0YXR1cyI6Im9mZiJ9LCJ2ZXJzaW9uIjoiMS4wIn19EkEKLQojCiEDYH6oSjForWVPKQaWSfgT3p9Rc16mDJTm0uXuiWuavMwSBAoCCAEYLBIQCgoKBXN0YWtlEgExEMCaDBpAChcefAalK6ZW85AGGbDhSQx/vWDAyG4A75/C3A7pLBYGMPVE3EBiti05p/6aoARYh02dyhpBEABGzjnSHBKHcw==\"}}"));
    }
}

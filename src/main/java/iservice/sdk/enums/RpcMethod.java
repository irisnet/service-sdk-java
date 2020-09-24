package iservice.sdk.enums;

/**
 * @author : ori
 * @date : 2020/9/22 5:18 下午
 */
public enum RpcMethod {
    /**
     * subscribe event
     */
    SUBSCRIBE("subscribe"),
    /**
     * unsubscribe event
     */
    UNSUBSCRIBE("unsubscribe"),
    /**
     * broadcast tx
     */
    BROADCAST_TX_SYNC("broadcast_tx_sync"),
    ;

    private final String method;


    RpcMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}

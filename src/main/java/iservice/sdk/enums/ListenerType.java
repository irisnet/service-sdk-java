package iservice.sdk.enums;

/**
 * @author : ori
 * @date : 2020/9/23 6:48 下午
 */
public enum ListenerType {
    /**
     * provider
     */
    PROVIDER("new_batch_request_provider"),
    /**
     * consumer
     */
    CONSUMER("response_service")
    ;
    private final String DOT = ".";
    private final String PARAM_PREFIX;

    ListenerType(String param_prefix) {
        PARAM_PREFIX = param_prefix;
    }

    public String getParamPrefix() {
        return PARAM_PREFIX;
    }

    public String getParamPrefixString(){
        return PARAM_PREFIX+DOT;
    }

    @Override
    public String toString() {
        return "ListenerType{" +
                "DOT='" + DOT + '\'' +
                ", PARAM_PREFIX='" + PARAM_PREFIX + '\'' +
                '}';
    }
}

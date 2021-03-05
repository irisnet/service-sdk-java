package iservice.sdk.enums;

/**
 * @author : ori
 * @date : 2020/9/23 7:03 下午
 */
public enum SubscribeQueryKeyEnum {
    /**
     * ---------------for provider
     */
    SERVICE_NAME("service_name"),
    PROVIDER("provider"),


    /**
     * ---------------for consumer
     */
    CONSUMER("consumer"),
    REQUEST_CONTEXT_ID("request_context_id"),
    ;
    private final String key;

    SubscribeQueryKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "SubscribeQueryKeyEnum{" +
                "key='" + key + '\'' +
                '}';
    }
}

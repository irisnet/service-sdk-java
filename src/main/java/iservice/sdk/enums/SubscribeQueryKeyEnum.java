package iservice.sdk.enums;

/**
 * @author : ori
 * @date : 2020/9/23 7:03 下午
 */
public enum SubscribeQueryKeyEnum {

    /**
     * ---------------------common
     */
    SERVICE_NAME("service_name"),

    /**
     * ---------------for provider
     */
    PROVIDER("provider"),


    /**
     * ---------------for consumer
     */
    MODULE("module"),
    SENDER("sender"),
    CONSUMER("consumer"),
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

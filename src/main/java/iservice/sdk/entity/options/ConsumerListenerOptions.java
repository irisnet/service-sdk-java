package iservice.sdk.entity.options;

import iservice.sdk.enums.ListenerType;

/**
 * @author : ori
 * @date : 2020/9/23 8:26 下午
 */
public class ConsumerListenerOptions extends ServiceListenerOptions {
    private final ListenerType LISTENER_TYPE = ListenerType.CONSUMER;
    private final String MODULE = "service";
    private String sender;
    private String serviceName;

    public ConsumerListenerOptions() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getModule() {
        return MODULE;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public ListenerType getListenerType() {
        return LISTENER_TYPE;
    }

    @Override
    public String toString() {
        return "ConsumerListenerOptions{" +
                "sender='" + sender + '\'' +
                ", module='" + MODULE + '\'' +
                '}';
    }
}

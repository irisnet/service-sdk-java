package iservice.sdk.entity.options;

import iservice.sdk.entity.ServiceListenerOptions;
import iservice.sdk.enums.ListenerType;

/**
 * @author : ori
 * @date : 2020/9/23 8:26 下午
 */
public class ConsumerListenerOptions extends ServiceListenerOptions {
    private final ListenerType LISTENER_TYPE = ListenerType.CONSUMER;
    private String sender;
    private String module;

    public ConsumerListenerOptions() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public ListenerType getListenerType() {
        return LISTENER_TYPE;
    }

    @Override
    public String toString() {
        return "ConsumerListenerOptions{" +
                "sender='" + sender + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}

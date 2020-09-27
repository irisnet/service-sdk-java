package iservice.sdk.entity.options;

import iservice.sdk.enums.ListenerType;

/**
 * @author : ori
 * @date : 2020/9/23 8:29 下午
 */
public class ProviderListenerOptions extends ServiceListenerOptions {

    private final ListenerType LISTENER_TYPE = ListenerType.PROVIDER;

    private String serviceName;

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
        return "ProviderListenerOptions{" +
                "LISTENER_TYPE=" + LISTENER_TYPE +
                ", serviceName='" + serviceName + '\'' +
                '}';
    }
}

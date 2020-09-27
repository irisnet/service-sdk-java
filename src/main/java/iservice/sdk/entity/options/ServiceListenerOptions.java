package iservice.sdk.entity.options;

import iservice.sdk.enums.ListenerType;

/**
 * Service listener config options
 *
 * @author Yelong
 */
public abstract class ServiceListenerOptions {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get listener's type
     *
     * @return
     */
    public abstract ListenerType getListenerType();

    @Override
    public String toString() {
        return "ServiceListenerOptions{" +
                "listenerType=" + getListenerType() +
                ", address='" + address + '\'' +
                '}';
    }
}

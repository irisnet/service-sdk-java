package iservice.sdk;

import iservice.sdk.impl.AbstractServiceListener;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.impl.ServiceClient;

import java.util.ArrayList;
import java.util.List;

public class ServiceClientBuilder {

    private ServiceClientOptions options;
    private List<AbstractServiceListener> listeners;

    protected ServiceClientBuilder() {
        this.listeners = new ArrayList<>();
    }

    public static final ServiceClientBuilder create() {
        return new ServiceClientBuilder();
    }

    public final ServiceClientBuilder setOptions(ServiceClientOptions options) {
        this.options = options;
        return this;
    }

    public final ServiceClientBuilder addListener(AbstractServiceListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public final ServiceClient build() {
        return new ServiceClient(options, listeners);
    }
}

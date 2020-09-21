package iservice.sdk;

import iservice.sdk.impl.AbstractServiceListener;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.impl.ServiceClient;
import iservice.sdk.module.IKeyDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for building service client instance
 */
public class ServiceClientBuilder {

    private ServiceClientOptions options;
    private List<AbstractServiceListener> listeners;
    private IKeyDAO keyDAO;

    protected ServiceClientBuilder() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Create a service client builder instance
     *
     * @return New service client builder
     */
    public static final ServiceClientBuilder create() {
        return new ServiceClientBuilder();
    }

    /**
     * Set service client configs
     *
     * @param options Service client configs
     * @return The builder itself
     */
    public final ServiceClientBuilder setOptions(ServiceClientOptions options) {
        this.options = options;
        return this;
    }

    /**
     * Add service event listener
     *
     * @param listener Service event listener
     * @return The builder itself
     */
    public final ServiceClientBuilder addListener(AbstractServiceListener listener) {
        this.listeners.add(listener);
        return this;
    }

    /**
     * Set Key DAO Implementation
     *
     * @param keyDAO Key DAO Implemention
     * @return The builder itself
     */
    public final ServiceClientBuilder withKeyDAO(IKeyDAO keyDAO) {
        this.keyDAO = keyDAO;
        return this;
    }

    /**
     * Build service client instance
     *
     * @return New service client instance
     */
    public final ServiceClient build() {
        ServiceClient serviceClient = ServiceClient.getInstance();
        serviceClient.setOptions(options);
        serviceClient.clearListeners();
        serviceClient.addListeners(listeners);
        return serviceClient;
    }
}

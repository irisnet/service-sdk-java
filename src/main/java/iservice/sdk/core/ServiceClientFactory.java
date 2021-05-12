package iservice.sdk.core;

import iservice.sdk.entity.options.ServiceClientOptions;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IKeyDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ori
 * @date : 2020/9/22 9:59 上午
 */
public class ServiceClientFactory {

    private volatile ServiceClient client;

    private ServiceClientOptions options;

    private final List<AbstractServiceListener> LISTENERS;
    private IKeyDAO keyDAO;

    private ServiceClientFactory() {
        LISTENERS = new ArrayList<>();
    }

    private static class ServiceClientFactoryHolder {
        private static final ServiceClientFactory INSTANCE = new ServiceClientFactory();
    }

    /**
     * Get {@link ServiceClientFactory} instance
     *
     * @return {@link ServiceClientFactory} instance
     */
    public static ServiceClientFactory getInstance() {
        return ServiceClientFactoryHolder.INSTANCE;
    }

    /**
     * Get service client instance
     *
     * @return {@link ServiceClient} instance
     */
    public ServiceClient getClient() {
        if (client == null) {
            synchronized (this) {
                if (client == null) {
                    validateParam();
                    client = new ServiceClient(options, LISTENERS, keyDAO);
                }
            }
        }
        return client;
    }

    private void validateParam() {
        if (options == null) {
            throw new ServiceSDKException("ServiceClientOptions are required!");
        }
    }

    /**
     * Set service client configs
     *
     * @param options Service client configs
     * @return The {@link ServiceClientFactory} itself
     */
    public final ServiceClientFactory setOptions(ServiceClientOptions options) {
        this.options = options;
        return this;
    }

    /**
     * Add service event listener
     *
     * @param listener Service event listener
     * @return The {@link ServiceClientFactory} itself
     */
    public final ServiceClientFactory addListener(AbstractServiceListener listener) {
        this.LISTENERS.add(listener);
        return this;
    }

    /**
     * Set Key DAO Implementation
     *
     * @param keyDAO Key DAO Implementation
     * @return The {@link ServiceClientFactory} itself
     */
    public final ServiceClientFactory setKeyDAO(IKeyDAO keyDAO) {
        this.keyDAO = keyDAO;
        return this;
    }

}

package iservice.sdk.entity;

import cosmos.base.v1beta1.CoinOuterClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Yelong
 */
public abstract class BaseServiceRequest<T> extends BaseRequest {

    public static final long DEFAULT_TIMEOUT = 100L;

    protected BaseServiceRequest() {
    }

    /**
     * Get the name of the service which is to be called
     *
     * @return serviceName
     */
    public abstract String getServiceName();

    /**
     * Get service provider list to request
     *
     * @return providers
     */
    public abstract List<String> getProviders();

    /**
     * Get maximum service fee to pay for a single request
     *
     * @return serviceFeeCap
     */
    public abstract List<CoinOuterClass.Coin> getServiceFeeCap();

    /**
     * Get request timeout in blocks
     *
     * @return timeout
     */
    public long getTimeout() {
        return DEFAULT_TIMEOUT;
    }

    /**
     * Get service request input
     *
     * @return Service request input
     */
    public abstract T getRequest();

    @Override
    public void validateParams() throws IllegalArgumentException {
        super.validateParams();
        if (StringUtils.isEmpty(this.getServiceName())) {
            throw new IllegalArgumentException("Service name is required");
        }
        if (this.getProviders() == null || this.getProviders().size() <= 0) {
            throw new IllegalArgumentException("Service provider addresses are required");
        }
        if (this.getServiceFeeCap() == null || this.getServiceFeeCap().size() <= 0) {
            throw new IllegalArgumentException("Service fee caps are required");
        }
        if (this.getRequest() == null) {
            throw new IllegalArgumentException("Service request is required");
        }
    }
}

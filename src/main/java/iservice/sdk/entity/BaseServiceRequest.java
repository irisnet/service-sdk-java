package iservice.sdk.entity;

import cosmos.base.v1beta1.CoinOuterClass;

import java.util.List;

/**
 * @author Yelong
 */
public abstract class BaseServiceRequest<T> extends BaseRequest<T> {

    protected BaseServiceRequest() {
    }

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

    @Override
    public void validateParams() throws IllegalArgumentException {
        super.validateParams();
        if (this.getProviders() == null || this.getProviders().size() <= 0) {
            throw new IllegalArgumentException("Service provider addresses are required");
        }
        if (this.getServiceFeeCap() == null || this.getServiceFeeCap().size() <= 0) {
            throw new IllegalArgumentException("Service fee caps are required");
        }

    }
}

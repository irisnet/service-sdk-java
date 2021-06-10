package iservice.sdk.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Yelong
 */
public abstract class BaseRequest<T> {
    protected BaseRequest() {
    }

    /**
     * Get the name of the key to sign txs
     *
     * @return keyName
     */
    public abstract String getKeyName();

    /**
     * Get password of the key
     *
     * @return keyPassword
     */
    public abstract String getKeyPassword();

    /**
     * Get the name of the service which is to be called
     *
     * @return serviceName
     */
    public abstract String getServiceName();

    /**
     * Get service request / response header
     *
     * @return Service request / response header
     */
    public abstract Header getHeader();

    /**
     * Get service request / response body
     *
     * @return Service request / response body
     */
    public abstract T getBody();

    /**
     * Validate params
     *
     * @throws IllegalArgumentException
     */
    public void validateParams() throws IllegalArgumentException {

        if (StringUtils.isEmpty(this.getKeyName())) {
            throw new IllegalArgumentException("Key name is required");
        }
        if (StringUtils.isEmpty(this.getKeyPassword())) {
            throw new IllegalArgumentException("Key password is required");
        }
        if (StringUtils.isEmpty(this.getServiceName())) {
            throw new IllegalArgumentException("Service name is required");
        }
        if (this.getBody() == null) {
            throw new IllegalArgumentException("Service request body is required");
        }
        if (this.getHeader() == null) {
            throw new IllegalArgumentException("Service request header is required");
        }
    }
}

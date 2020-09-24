package iservice.sdk.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Yelong
 */
public abstract class BaseRequest {

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
    }
}

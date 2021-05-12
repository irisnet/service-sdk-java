package iservice.sdk.core;

import iservice.sdk.entity.options.ServiceListenerOptions;
import org.bouncycastle.crypto.CryptoException;

/**
 * @author Yelong
 */
public abstract class AbstractServiceListener<T> {

    /**
     * Get options of the listener
     *
     * @return {@link ServiceListenerOptions}
     */
    public abstract ServiceListenerOptions getOptions();

    /**
     * Business method to implement.
     *
     * @param json
     */
    public abstract void callback(String json) throws CryptoException;

    /**
     * get request object like {@link T} from json string
     *
     * @param json request json string
     * @return T
     */
    abstract T getReqFromJson(String json) throws CryptoException;

    /**
     * return class of req
     *
     * @return
     */
    protected abstract Class<T> getReqClass();

}

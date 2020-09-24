package iservice.sdk.core;

import iservice.sdk.entity.ServiceListenerOptions;

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
    public abstract void callback(String json);

    /**
     * get request object like {@link T} from json string
     *
     * @param json request json string
     * @return T
     */
    protected abstract T getReqFromJson(String json);

    /**
     * return class of req
     *
     * @return
     */
    protected abstract Class<T> getReqClass();

    protected abstract boolean checkValidate(T res);
}

package iservice.sdk.core;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * @author mitch
 * @date 2020/9/16
 */
public abstract class AbstractServiceListener<T> {

    /**
     * getOptions
     *
     * @return
     */
    abstract ServiceListenerOptions getOptions();

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

    protected abstract boolean checkValidate(T res);
}

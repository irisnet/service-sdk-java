package iservice.sdk.impl;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public abstract class AbstractServiceListener<T> {

    abstract ServiceListenerOptions getOptions();
    abstract void callback(T obj);
}

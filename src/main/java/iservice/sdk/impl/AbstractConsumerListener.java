package iservice.sdk.impl;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public abstract class AbstractConsumerListener<T> extends AbstractServiceListener<T> {

    @Override
    final void callback(Object obj) {
        // xxx
        onResponse((T) obj);
    }

    @Override
    public abstract ServiceListenerOptions getOptions();
    public abstract void onResponse(T res);
}
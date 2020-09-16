package iservice.sdk.impl;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public abstract class AbstractProviderListener<T, R> extends AbstractServiceListener<T> {

    void callback(Object obj) {
        // TODO 解析
        R res = onRequest((T) obj);
        // TODO 创建 tx 并签名广播
    }

    @Override
    public abstract ServiceListenerOptions getOptions();
    public abstract R onRequest(T req);
}
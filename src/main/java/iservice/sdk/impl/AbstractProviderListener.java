package iservice.sdk.impl;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public abstract class AbstractProviderListener<T, R> extends AbstractServiceListener<T> {

    @Override
    public void callback(String json) {
        T req = getReqFromJson(json);
        if (!checkValidate(req)) {
            return;
        }
        R res = onRequest(req);
        // TODO 创建 tx 并签名广播
        ServiceClient.getInstance().callService(res);
    }

    @Override
    public abstract ServiceListenerOptions getOptions();

    /**
     * deal with business
     *
     * @param req
     * @return
     */
    public abstract R onRequest(T req);
}
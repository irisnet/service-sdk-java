package iservice.sdk.core;

import iservice.sdk.entity.ServiceListenerOptions;

/**
 * Created by mitch on 2020/9/16.
 */
public abstract class AbstractConsumerListener<T> extends AbstractServiceListener<T> {

    @Override
    public final void callback(String json) {
        T res = getReqFromJson(json);
        if (!checkValidate(res)) {
            return;
        }
        onResponse(res);
    }

    @Override
    public abstract ServiceListenerOptions getOptions();
    public abstract void onResponse(T res);
}
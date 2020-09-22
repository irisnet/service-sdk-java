package iservice.sdk.core;

import iservice.sdk.entity.ServiceListenerOptions;
import iservice.sdk.net.WebSocketClient;

/**
 * @author Yelong
 */
public abstract class AbstractProviderListener<T, R> extends AbstractServiceListener<T> {

    private ServiceClient client;

    @Override
    public void callback(String json) {
        T req = getReqFromJson(json);
        if (!checkValidate(req)) {
            return;
        }
        R res = onRequest(req);
        // TODO 创建 tx 并签名广播
        sendRes(res);
    }

    private void sendRes(R res) {
        if (client == null) {
            client = ServiceClientFactory.getInstance().getClient();
        }
        client.sendMsg(res);
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
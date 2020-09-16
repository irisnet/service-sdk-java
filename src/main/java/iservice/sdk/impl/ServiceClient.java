package iservice.sdk.impl;

import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.ServiceClientOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitch on 2020/9/16.
 */
public class ServiceClient {

    private ServiceClientOptions options;
    private List<AbstractServiceListener> listeners;

    public ServiceClient(ServiceClientOptions options, List<AbstractServiceListener> listeners) {
        this.options = options;
        this.listeners = listeners != null ? listeners : new ArrayList<>();
    }

    public void callService(BaseServiceRequest req) {
        // TODO 创建 tx 并签名广播
        System.out.println("Sending request ...");
        System.out.println(req.toString());

        // TODO removing test callback
        this.listeners.forEach(listener -> {
            if (listener instanceof AbstractConsumerListener) {
                return;
            }
            listener.callback(req);
        });
    }

}

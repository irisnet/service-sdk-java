package iservice.sdk.impl;

import com.alibaba.fastjson.JSON;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.entity.WrappedMessage;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.net.WebSocketClient;
import iservice.sdk.net.WebSocketClientOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitch on 2020/9/16.
 */
public final class ServiceClient {

    private ServiceClientOptions optionsCache;
    private ServiceClientOptions options;

    private final List<AbstractServiceListener> LISTENERS_CACHE = new ArrayList<>();
    private final List<AbstractServiceListener> LISTENERS = new ArrayList<>();

    private volatile WebSocketClient webSocketClient = null;


    /**
     * ------------ Singleton start------------
     **/
    private static class ServiceClientHolder {
        private static final ServiceClient INSTANCE = new ServiceClient();
    }

    private ServiceClient() {
    }

    public static ServiceClient getInstance() {
        return ServiceClientHolder.INSTANCE;
    }

    /**
     * ------------ Singleton end------------
     **/

    public void startWebSocketClient() {
        if (options.getUri() == null) {
            throw new WebSocketConnectException("uri is null!");
        }
        if (webSocketClient == null) {
            synchronized (this) {
                if (webSocketClient == null) {
                    WebSocketClientOption webSocketClientOption = new WebSocketClientOption();
                    webSocketClientOption.setUri(options.getUri());
                    webSocketClient = new WebSocketClient(webSocketClientOption);
                }
            }
        }
        webSocketClient.start();
    }

    public <T> void callService(T msg) {
        webSocketClient.send(JSON.toJSONString(new WrappedMessage<>(msg)));
    }

    public void doNotifyAllListener(String msg) {
        // TODO 创建 tx 并签名广播
        System.out.println("Sending request ...");
        System.out.println(msg);

        this.LISTENERS.forEach(listener -> {
            listener.callback(msg);
        });
    }

    /**
     *
     * @param options
     */
    public void setOptions(ServiceClientOptions options) {
        this.optionsCache = options;
    }

    public void refreshListeners(List<AbstractServiceListener> listeners) {
        clearListeners();
        addListeners(listeners);
    }

    public void addListeners(List<AbstractServiceListener> listeners) {
        this.LISTENERS_CACHE.addAll(listeners);
    }

    public void clearListeners() {
        this.LISTENERS_CACHE.clear();
    }

}

package iservice.sdk.core;

import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.impl.DefaultKeyServiceImpl;
import iservice.sdk.net.WebSocketClient;
import iservice.sdk.net.WebSocketClientOptions;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yelong
 */
public final class ServiceClient {

    private ServiceClientOptions options;

    private final List<AbstractServiceListener> LISTENERS = new ArrayList<>();

    private IKeyDAO keyDAO;

    private volatile WebSocketClient webSocketClient = null;

    ServiceClient(ServiceClientOptions options, List<AbstractServiceListener> listeners, IKeyDAO keyDAO) {
        this.options = options;
        this.LISTENERS.addAll(listeners);
        this.keyDAO = keyDAO;
    }

    /**
     * Start WebSocket Client
     */
    public void startWebSocketClient() {
        if (options.getUri() == null) {
            throw new WebSocketConnectException("WebSocket uri is undefined");
        }
        if (webSocketClient == null) {
            synchronized (this) {
                if (webSocketClient == null) {
                    WebSocketClientOptions webSocketClientOptions = new WebSocketClientOptions();
                    webSocketClientOptions.setUri(options.getUri());
                    webSocketClient = new WebSocketClient(webSocketClientOptions);
                }
            }
        }
        webSocketClient.start();
    }

    /**
     * Send remote service request
     *
     * @param msg Service request
     * @param <T> Service request object type
     */
    public <T> void callService(T msg) {
        webSocketClient.send(msg);
    }

    /**
     * Get key management service
     *
     * @return {@link IKeyService} implementation
     */
    public IKeyService getKeyService() {
        switch (this.options.getSignAlgo()) {
            case SM2:
                throw new NotImplementedException("SM2 not implemented");
            default:
                return new DefaultKeyServiceImpl(this.keyDAO);
        }
    }

    /**
     * Send msg to the blockchain
     *
     * @param msg Msg to send
     * @param <T> Msg type
     */
    <T> void sendMsg(T msg) {
        webSocketClient.send(msg);
    }

    /**
     * Listening from blockchain and notify listeners
     *
     * @param msg Msg from blockchain
     */
    void doNotifyAllListener(String msg) {
        this.LISTENERS.forEach(listener -> {
            listener.callback(msg);
        });
    }
}

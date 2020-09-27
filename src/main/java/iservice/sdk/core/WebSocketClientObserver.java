package iservice.sdk.core;

import iservice.sdk.exception.ServiceException;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.net.observer.events.ConnectEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * @author : ori
 * @date : 2020/9/21 8:14 下午
 */
public class WebSocketClientObserver implements Observer {

    private ServiceClient client = ServiceClientFactory.getInstance().getClient();

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof ConnectEvent)) {
            return;
        }
        ConnectEvent event = (ConnectEvent) arg;
        switch (event.getEventType()) {
            case ON_OPEN:
                doOnOpen();
                break;
            case ON_MESSAGE:
                doOnMessage(event.getData());
                break;
            case ON_CLOSE:
                doOnClose();
                break;
            case ON_ERROR:
                doOnError(event.getCause());
                break;
            default:
        }
    }

    private void doOnOpen() {
    }

    private void doOnMessage(String jsonData) {
        client.doNotifyListeners(jsonData);
    }

    private void doOnClose() {
        client.restartWebSocketClient();
    }

    private void doOnError(Throwable cause) {
        if (cause instanceof ServiceException) {
            // do sth. with exception info
            cause.printStackTrace();
        } else if (cause instanceof ServiceSDKException) {
            cause.printStackTrace();
        } else if (cause instanceof WebSocketConnectException) {
            client.restartWebSocketClient();
        } else {
            cause.printStackTrace();
        }
    }
}

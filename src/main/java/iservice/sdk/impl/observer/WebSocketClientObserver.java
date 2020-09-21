package iservice.sdk.impl.observer;

import iservice.sdk.impl.ServiceClient;
import iservice.sdk.impl.observer.events.ConnectEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : ori
 * @date : 2020/9/21 8:14 下午
 */
public class WebSocketClientObserver implements Observer {

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
                doOnError();
                break;
            default:
        }
    }

    private void doOnOpen() {

    }

    private void doOnMessage(String jsonData) {
        ServiceClient.getInstance().doNotifyAllListener(jsonData);
    }

    private void doOnClose() {

    }

    private void doOnError() {

    }
}

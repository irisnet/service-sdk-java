package iservice.sdk.net.observer.events;

/**
 * @author : ori
 * @date : 2020/9/21 2:33 下午
 */
public class ConnectEvent {

    private final ConnectEventType eventType;

    private String data;

    /**
     * Constructs a prototypical Event.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public ConnectEvent(ConnectEventType eventType) {
        this.eventType = eventType;
    }

    public ConnectEvent(ConnectEventType eventType, String data) {
        this.eventType = eventType;
        this.data = data;
    }

    public ConnectEventType getEventType() {
        return eventType;
    }

    public String getData() {
        return data;
    }
}

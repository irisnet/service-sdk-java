package iservice.sdk.net.observer.events;

/**
 * @author : ori
 * @date : 2020/9/21 2:33 下午
 */
public class ConnectEvent {

    private final ConnectEventType eventType;

    private String data;

    private Throwable cause;

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

    public ConnectEvent(ConnectEventType eventType, Throwable cause) {
        this.eventType = eventType;
        this.cause = cause;
    }

    public ConnectEventType getEventType() {
        return eventType;
    }

    public String getData() {
        return data;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "ConnectEvent{" +
                "eventType=" + eventType +
                ", data='" + data + '\'' +
                ", cause=" + cause +
                '}';
    }
}

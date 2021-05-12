package irita.sdk.module.base;

import java.util.List;

public class Logs {
    private List<StringEvent> events;

    public void setEvents(List<StringEvent> events) {
        this.events = events;
    }

    public List<StringEvent> getEvents() {
        return events;
    }
}
package irita.sdk.module.base;

import irita.sdk.constant.enums.EventEnum;

import java.util.List;

public class Logs {
    private List<StringEvent> events;

    public void setEvents(List<StringEvent> events) {
        this.events = events;
    }

    public List<StringEvent> getEvents() {
        return events;
    }

    public String getEventValue(EventEnum eventEnum) {
        for (StringEvent e : events) {
            if (eventEnum.getType().equals(e.getType())) {
                for (StringEvent.Attribute attr : e.getAttributes()) {
                    if (eventEnum.getKey().equals(attr.key)) {
                        return attr.value;
                    }
                }
            }
        }
        return "";
    }
}
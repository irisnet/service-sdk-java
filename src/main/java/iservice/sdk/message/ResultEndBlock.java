package iservice.sdk.message;

import java.util.List;

/**
 * @author : ori
 * @date : 2020/9/24 12:08 下午
 */
public class ResultEndBlock {
    private List<ResultEvents> events;

    public List<ResultEvents> getEvents() {
        return events;
    }

    public void setEvents(List<ResultEvents> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "ResultEndBlock{" +
                "events=" + events +
                '}';
    }
}

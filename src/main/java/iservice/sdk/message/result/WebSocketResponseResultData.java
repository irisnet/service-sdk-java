package iservice.sdk.message.result;

/**
 * @author : ori
 * @date : 2020/9/24 12:04 下午
 */
public class WebSocketResponseResultData<T> {
    private String type;
    private T value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WebSocketResponseResultData{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}

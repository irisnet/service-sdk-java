package iservice.sdk.message.result;

import java.util.Base64;

/**
 * @author : ori
 * @date : 2020/9/24 1:20 下午
 */
public class EventAttribute {
    /**
     * Should be a base64 code
     */
    private String key;
    /**
     * Should be a base64 code
     */
    private String value;
    private Boolean index;

    public EventAttribute getDecodeAttribute(){
        EventAttribute attribute = new EventAttribute();
        attribute.setKey(new String(getKeyBase64()));
        attribute.setValue(new String(getValueBase64()));
        attribute.setIndex(this.index);
        return attribute;
    }

    public byte[] getKeyBase64(){
        return Base64.getDecoder().decode(key);
    }

    public byte[] getValueBase64(){
        return Base64.getDecoder().decode(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIndex() {
        return index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "EventAttribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", index=" + index +
                '}';
    }
}

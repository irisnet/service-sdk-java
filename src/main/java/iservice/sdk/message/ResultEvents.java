package iservice.sdk.message;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : ori
 * @date : 2020/9/24 12:09 下午
 */
public class ResultEvents {
    private String type;
    private List<EventAttribute> attributes;
    private Map<String,EventAttribute> decodedAttributeMap;

    public void getDecodeAttributes(){
        if (attributes == null) {
            return ;
        }
        decodedAttributeMap =  attributes.stream()
                .map(EventAttribute::getDecodeAttribute)
                .collect(Collectors.toMap(EventAttribute::getKey,o->o));
    }

    public String getAttributesValueByKey(String key){
        if (decodedAttributeMap==null) {
            getDecodeAttributes();
        }
        EventAttribute attribute = decodedAttributeMap.get(key);
        return attribute == null?null:attribute.getValue();
    }

    /**
     * this{@link ResultEvents#getDecodeAttributes()}
     *
     * @param key
     * @param value
     * @return
     */
    public boolean compareAttribute(String key, String value){
        String attributeValue = getAttributesValueByKey(key);
        return attributeValue != null && attributeValue.equals(value);
    }

    public boolean equalsType(String type){
        return Objects.equals(type,this.type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EventAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<EventAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "ResultBlockEvents{" +
                "type='" + type + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}

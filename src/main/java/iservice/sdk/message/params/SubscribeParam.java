package iservice.sdk.message.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iservice.sdk.enums.ListenerType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : ori
 * @date : 2020/9/23 6:16 下午
 */
public class SubscribeParam extends BaseParam {
    private String query;

    @JsonIgnore
    private ListenerType listenerType;

    @JsonIgnore
    private List<SubscribeCondition> conditions = new ArrayList<>();

    public SubscribeParam(ListenerType listenerType) {
        this.listenerType = listenerType;
    }

    public SubscribeParam addCondition(SubscribeCondition condition) {
        conditions.add(condition);
        return this;
    }

    /**
     * It must be invoked before transform it to Json.
     *
     * @return
     */
    public SubscribeParam generateQueryString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < conditions.size(); i++) {
            if (StringUtils.isNotEmpty(conditions.get(i).getValue())) {
                if (i > 0) {
                    buffer.append(" AND ");
                }
                buffer.append(listenerType.getParamPrefixString())
                        .append(conditions.get(i).getConditionString());
            }
        }
        this.query = buffer.toString();
        return this;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return "SubscribeParam{" +
                "query='" + query + '\'' +
                ", listenerType=" + listenerType +
                ", conditions=" + conditions +
                '}';
    }
}

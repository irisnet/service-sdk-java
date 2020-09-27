package iservice.sdk.util;

import com.alibaba.fastjson.JSON;
import iservice.sdk.entity.options.ServiceListenerOptions;
import iservice.sdk.entity.options.ConsumerListenerOptions;
import iservice.sdk.entity.options.ProviderListenerOptions;
import iservice.sdk.enums.SubscribeQueryKeyEnum;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.message.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : ori
 * @date : 2020/9/23 10:17 下午
 */
public class DecodeUtil {
    public static String decodeProviderReq(String json, ProviderListenerOptions options) {
        json = formatJson(json);
        ServiceReqMessage x = JSON.parseObject(json, ServiceReqMessage.class);
        ServiceReqResult result = x.getResult();
        if (!checkMessageType(result, options, WebSocketResponseResultData.REQ_TYPE)) {
            return null;
        }
        WebSocketResponseResultData<WebSocketResponseResultDataBlockInfo> data = result.getData();
        ResultEndBlock resultEndBlock = data.getValue().getResult_end_block();
        List<ResultEvents> events = filterEventsByKey(options.getListenerType().getParamPrefix(), resultEndBlock.getEvents());
        ResultEvents targetEvent = events.stream().filter(event -> {
            event.getDecodeAttributes();
            return event.compareAttribute(SubscribeQueryKeyEnum.PROVIDER.getKey(), options.getAddress())
                    && event.compareAttribute(SubscribeQueryKeyEnum.SERVICE_NAME.getKey(), options.getServiceName());
        }).findAny().orElse(null);
        if (targetEvent == null) {
            throw new ServiceSDKException("Listener info not found! serviceName='" + options.getServiceName() + "' providerAddress='" + options.getAddress() + "'");
        }
        String requestsJson = targetEvent.getAttributesValueByKey("requests");
        return JSON.parseArray(requestsJson, String.class).get(0);
    }

    public static String decodeConsumerReq(String json, ConsumerListenerOptions options) {
        json = formatJson(json);
        ServiceResMessage message = JSON.parseObject(json, ServiceResMessage.class);
        ServiceResResult messageResult = message.getResult();
        if (!checkMessageType(messageResult, options, WebSocketResponseResultData.RES_TYPE)) {
            return null;
        }
        TxResultInfo result = messageResult.getData().getValue().getTxResult().getResult();
        List<ResultEvents> events = filterEventsByKey(options.getListenerType().getParamPrefix(), result.getEvents());
        ResultEvents targetEvent = events.stream().filter(event -> {
            event.getDecodeAttributes();
            return event.compareAttribute(SubscribeQueryKeyEnum.CONSUMER.getKey(), options.getAddress())
                    && event.compareAttribute(SubscribeQueryKeyEnum.SENDER.getKey(), options.getSender())
                    && event.compareAttribute(SubscribeQueryKeyEnum.MODULE.getKey(), options.getModule())
                    && event.compareAttribute(SubscribeQueryKeyEnum.SERVICE_NAME.getKey(), options.getServiceName())
                    ;
        }).findAny().orElse(null);
        if (targetEvent == null) {
            throw new ServiceSDKException("Listener info not found! ListenerOption=" + JSON.toJSONString(options));
        }
        return targetEvent.getAttributesValueByKey("request_id");

    }

    private static <T> boolean checkMessageType(BaseServiceResult<T> messageResult, ServiceListenerOptions options, String requireType) {
        return Objects.equals(messageResult.getQuery(), SubscribeUtil.buildSubscribeParam(options).getQuery()) && messageResult.getData() != null && Objects.equals(messageResult.getData().getType(), requireType);
    }

    public static String formatJson(String json) {
        return json.replaceAll("}[ \\n]*\\{[\\s\\S]+$", "}");
    }

    private static List<ResultEvents> filterEventsByKey(String type, List<ResultEvents> events) {
        return events.stream()
                .filter(event -> event.equalsType(type))
                .collect(Collectors.toList());
    }
}

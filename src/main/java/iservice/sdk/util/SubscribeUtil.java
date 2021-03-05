package iservice.sdk.util;

import iservice.sdk.core.AbstractServiceListener;
import iservice.sdk.entity.options.ServiceListenerOptions;
import iservice.sdk.entity.options.ConsumerListenerOptions;
import iservice.sdk.entity.options.ProviderListenerOptions;
import iservice.sdk.enums.RpcMethod;
import iservice.sdk.enums.SubscribeQueryKeyEnum;
import iservice.sdk.message.WrappedMessage;
import iservice.sdk.message.params.SubscribeCondition;
import iservice.sdk.message.params.SubscribeParam;

import java.util.UUID;

/**
 * @author : ori
 * @date : 2020/9/23 8:34 下午
 */
public class SubscribeUtil {

    public static WrappedMessage<SubscribeParam> buildSubscribeMessage(AbstractServiceListener listener) {
        ServiceListenerOptions options = listener.getOptions();
        WrappedMessage<SubscribeParam> message = new WrappedMessage<>();
        message.setId(UUID.randomUUID().toString());
        message.setMethod(RpcMethod.SUBSCRIBE.getMethod());
        SubscribeParam params = buildSubscribeParam(options);
        message.setParams(params);
        return message;
    }

    public static SubscribeParam buildSubscribeParam(ServiceListenerOptions options) {
        SubscribeParam params = new SubscribeParam(options.getListenerType());
        switch (options.getListenerType()) {
            case PROVIDER:
                ProviderListenerOptions providerListenerOptions = (ProviderListenerOptions) options;
                params.addCondition(new SubscribeCondition(SubscribeQueryKeyEnum.SERVICE_NAME).eq(providerListenerOptions.getServiceName()))
                        .addCondition(new SubscribeCondition(SubscribeQueryKeyEnum.PROVIDER).eq(options.getAddress()));
                break;
            case CONSUMER:
                ConsumerListenerOptions consumerListenerOptions = (ConsumerListenerOptions) options;
                params.addCondition(new SubscribeCondition(SubscribeQueryKeyEnum.CONSUMER).eq(consumerListenerOptions.getAddress()))
                        .addCondition(new SubscribeCondition(SubscribeQueryKeyEnum.REQUEST_CONTEXT_ID).eq(consumerListenerOptions.getRequestContextID()));
                break;
            default:
        }
        params.generateQueryString();
        return params;
    }
}

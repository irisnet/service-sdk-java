package iservice.sdk.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import irismod.service.QueryGrpc;
import irismod.service.QueryOuterClass;
import irismod.service.Service;

import iservice.sdk.entity.ServiceMessage;
import iservice.sdk.entity.options.ConsumerListenerOptions;
import iservice.sdk.exception.ServiceException;
import iservice.sdk.message.BusinessResponseResult;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.util.DecodeUtil;

/**
 * @author Yelong
 * @date 2020/9/16
 */
public abstract class AbstractConsumerListener<T> extends AbstractServiceListener<T> {

    @Override
    public final void callback(String json) {
        T res = getReqFromJson(json);
        if (res == null) {
            return;
        }
        onResponse(res);
    }

    @Override
    public abstract ConsumerListenerOptions getOptions();

    public abstract void onResponse(T res);

    @Override
    T getReqFromJson(String json) {
        String requestId = DecodeUtil.decodeConsumerReq(json, getOptions());
        if (requestId == null) {
            return null;
        }
        String responseOutputByRequestContextIdJson = getResponseOutputByRequestContextId(requestId);

        ServiceMessage<T> message = JSON.parseObject(responseOutputByRequestContextIdJson, new TypeReference<ServiceMessage<T>>() {
        }.getType());
        return JSON.parseObject(JSON.toJSONString(message.getBody()),getReqClass());
    }

    private String getResponseOutputByRequestContextId(String requestId) {
        QueryGrpc.QueryBlockingStub queryBlockingStub
                = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
        QueryOuterClass.QueryResponseResponse responseResponse = queryBlockingStub.response(
                QueryOuterClass.QueryResponseRequest.newBuilder()
                        .setRequestId(requestId)
                        .build()
        );
        Service.Response response = responseResponse.getResponse();
        BusinessResponseResult responseResult = JSON.parseObject(response.getResult(), BusinessResponseResult.class);
        if (!responseResult.isSuccess()) {
            throw new ServiceException(responseResult.getMessage());
        }
        return response.getOutput();
    }
}
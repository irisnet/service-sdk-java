package iservice.sdk.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.QueryGrpc;
import irismod.service.QueryOuterClass;
import irismod.service.Service;
import iservice.sdk.entity.BaseServiceResponse;
import iservice.sdk.entity.ServiceMessage;
import iservice.sdk.entity.WrappedRequest;
import iservice.sdk.entity.options.ProviderListenerOptions;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.net.HttpClient;
import iservice.sdk.util.DecodeUtil;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yelong
 */
public abstract class AbstractProviderListener<T, B, R extends BaseServiceResponse<B>> extends AbstractServiceListener<T> {

    @Override
    public void callback(String json) {
        getReqFromJson(json);
    }

    @Override
    T getReqFromJson(String json) {
        String requests = DecodeUtil.decodeProviderReq(json, getOptions());
        if (requests == null) {
            return null;
        }
        // batch query with requests
        Service.Request sr = getRequestInputByRequest(requests);
        ServiceMessage<T> message = JSON.parseObject(sr.getInput(), new TypeReference<ServiceMessage<T>>() {}.getType());
        T req = JSON.parseObject(JSON.toJSONString(message.getBody()),getReqClass());
        R res = onRequest(req);
        try {
            sendRes(res, sr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return req;
    }

    private Service.Request getRequestInputByRequest(String requestId) {
        QueryGrpc.QueryBlockingStub queryBlockingStub
                = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
        QueryOuterClass.QueryRequestResponse request = queryBlockingStub.request(
                QueryOuterClass.QueryRequestRequest.newBuilder()
                        .setRequestId(ByteString.copyFrom(Hex.decode(requestId)))
                        .build());
        return request.getRequest();
    }

    private void sendRes(R res, Service.Request sr) throws IOException {
        String outputJson = JSON.toJSONString(new ServiceMessage<>(res.getHeader(), res.getBody()));

        Service.MsgRespondService msg = Service.MsgRespondService.newBuilder()
                .setRequestId(sr.getId())
                .setOutput(outputJson)
                .setResult("{\"code\": 200, \"message\": \"success\"}")
                .setProvider(sr.getProvider())
                .build();

        TxOuterClass.TxBody body = TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msg, "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();

        ServiceClient client = ServiceClientFactory.getInstance().getClient();
        TxOuterClass.Tx tx = client.getTxService().signTx(body, res.getKeyName(), res.getKeyPassword(), false);

        Map<String, String> params = new HashMap<>();
        params.put("tx", Base64.getEncoder().encodeToString(tx.toByteArray()));
        WrappedRequest<Map<String, String>> wrappedRequest = new WrappedRequest<>(params);
        wrappedRequest.setMethod("broadcast_tx_sync");
        String result = HttpClient.getInstance().post(client.getOptions().getRpcURI().toString(), JSON.toJSONString(wrappedRequest));
        // TODO error handler
        System.out.println(result);
    }

    @Override
    public abstract ProviderListenerOptions getOptions();

    /**
     * deal with business
     *
     * @param req
     * @return
     */
    public abstract R onRequest(T req);
}
package iservice.sdk.core;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import irismod.service.QueryGrpc;
import irismod.service.QueryOuterClass;
import iservice.sdk.entity.options.ProviderListenerOptions;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.util.DecodeUtil;
import org.bouncycastle.util.encoders.Hex;

/**
 * @author Yelong
 */
public abstract class AbstractProviderListener<T, R> extends AbstractServiceListener<T> {

    private ServiceClient client;

    @Override
    public void callback(String json) {
        T req = getReqFromJson(json);
        if (req!= null) {
            return;
        }
        R res = onRequest(req);
        // TODO 创建 tx 并签名广播
        sendRes(res);
    }

    @Override
    protected T getReqFromJson(String json) {
        String requests = DecodeUtil.decodeProviderReq(json, getOptions());
        if (requests == null) {
            return null;
        }
        // batch query with requests
        String requestJson = getRequestInputByRequest(requests);
        return JSON.parseObject(requestJson, getReqClass());
    }

    private String getRequestInputByRequest(String requestId) {
        QueryGrpc.QueryBlockingStub queryBlockingStub
                = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
        QueryOuterClass.QueryRequestResponse request = queryBlockingStub.request(
                QueryOuterClass.QueryRequestRequest.newBuilder()
                        .setRequestId(ByteString.copyFrom(Hex.decode(requestId)))
                        .build());
        return request.getRequest().getInput();
    }

    private void sendRes(R res) {
        if (client == null) {
            client = ServiceClientFactory.getInstance().getClient();
        }
//        client.sendMsg(res);
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
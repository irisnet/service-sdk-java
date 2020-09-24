package iservice.sdk.core;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.base.crypto.v1beta1.Crypto;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.Service;
import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.impl.DefaultKeyServiceImpl;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.net.WebSocketClient;
import iservice.sdk.net.WebSocketClientOptions;
import iservice.sdk.util.Bech32Utils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Yelong
 */
public final class ServiceClient {

    private ServiceClientOptions options;

    private final List<AbstractServiceListener> LISTENERS = new ArrayList<>();

    private IKeyDAO keyDAO;

    private IKeyService keyService;

    private volatile WebSocketClient webSocketClient = null;

    ServiceClient(ServiceClientOptions options, List<AbstractServiceListener> listeners, IKeyDAO keyDAO) {
        this.options = options;
        this.LISTENERS.addAll(listeners);
        this.keyDAO = keyDAO;
        GrpcChannel.INSTANCE.setURL(options.getUri().toString());
        switch (this.options.getSignAlgo()) {
            case SM2:
                throw new NotImplementedException("SM2 not implemented");
            default:
                this.keyService = new DefaultKeyServiceImpl(this.keyDAO);
        }
    }

    /**
     * Start WebSocket Client
     */
    public void startWebSocketClient() {
        if (options.getUri() == null) {
            throw new WebSocketConnectException("WebSocket uri is undefined");
        }
        if (webSocketClient == null) {
            synchronized (this) {
                if (webSocketClient == null) {
                    WebSocketClientOptions webSocketClientOptions = new WebSocketClientOptions();
                    webSocketClientOptions.setUri(options.getUri());
                    webSocketClient = new WebSocketClient(webSocketClientOptions);
                }
            }
        }
        webSocketClient.start();
    }

    /**
     * Send remote service request
     *
     * @param req Service request
     * @param <T> Service request object type
     */
    public <T> void callService(BaseServiceRequest<T> req) {


        String inputJson = JSON.toJSONString(req.getRequest());

        Service.MsgCallService.Builder msgBuilder = Service.MsgCallService.newBuilder();
        req.getProviders().forEach(address -> {
            msgBuilder.addProviders(ByteString.copyFrom(Bech32Utils.fromBech32(address)));
        });

        msgBuilder.setConsumer(ByteString.copyFrom(Bech32Utils.fromBech32(this.getKeyService().showAddress(req.getKeyName()))))
                .setServiceName(req.getServiceName())
                .setInput(inputJson)
                .addAllServiceFeeCap(req.getServiceFeeCap())
                .setTimeout(req.getTimeout())
                .setSuperMode(false)
                .setRepeated(false)
                .setRepeatedFrequency(0)
                .setRepeatedTotal(0);

        TxOuterClass.TxBody body = TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msgBuilder.build(), "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();

        this.getKeyService().signTx(body, req.getKeyName(), req.getKeyPassword(), false);
//        webSocketClient.send(msg);
    }

    /**
     * Get key management service
     *
     * @return {@link IKeyService} implementation
     */
    public IKeyService getKeyService() {
        return this.keyService;
    }

    /**
     * Send msg to the blockchain
     *
     * @param msg Msg to send
     * @param <T> Msg type
     */
    <T> void sendMsg(T msg) {
        webSocketClient.send(msg);
    }

    /**
     * Listening from blockchain and notify listeners
     *
     * @param msg Msg from blockchain
     */
    void doNotifyAllListener(String msg) {
        this.LISTENERS.forEach(listener -> {
            listener.callback(msg);
        });
    }
}

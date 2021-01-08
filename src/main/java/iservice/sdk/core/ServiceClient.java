package iservice.sdk.core;

import iservice.sdk.entity.options.TxOptions;
import org.bouncycastle.crypto.CryptoException;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.*;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.QueryGrpc;
import irismod.service.Service;

import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.options.ServiceClientOptions;
import iservice.sdk.entity.ServiceMessage;
import iservice.sdk.entity.WrappedRequest;
import iservice.sdk.exception.WebSocketConnectException;
import iservice.sdk.message.WrappedMessage;
import iservice.sdk.message.params.SubscribeParam;
import iservice.sdk.module.IAuthService;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.ITxService;
import iservice.sdk.module.impl.*;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.net.HttpClient;
import iservice.sdk.net.WebSocketClient;
import iservice.sdk.net.WebSocketClientOptions;
import iservice.sdk.util.Bech32Utils;
import iservice.sdk.util.SubscribeUtil;

/**
 * @author Yelong
 */
public final class ServiceClient {

  public static Logger logger = Logger.getLogger(ServiceClient.class);

  private ServiceClientOptions options;

  private final List<AbstractServiceListener> LISTENERS = new ArrayList<>();

  private IKeyDAO keyDAO;

  private IKeyService keyService;
  private IAuthService authService;
  private ITxService txService;

  private String chainID;
  private String amount;
  private String denom;
  private int gasLimit;

  private volatile WebSocketClient webSocketClient = null;
  private QueryGrpc.QueryBlockingStub serviceBlockingStub;

  ServiceClient(ServiceClientOptions options, List<AbstractServiceListener> listeners, IKeyDAO keyDAO) {
    this.options = options;
    this.LISTENERS.addAll(listeners);
    this.keyDAO = keyDAO;
    GrpcChannel.getInstance().setURL(options.getGrpcURI().getHost().concat(":").concat(options.getGrpcURI().getPort() + ""));
    serviceBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
  }

  public ServiceClientOptions getOptions() {
    return options;
  }

  public void start() {
    startWebSocketClient();
    subscribeAllListener();
  }

  /**
   * Start WebSocket Client
   */
  public void startWebSocketClient() {
    if (options.getRpcURI() == null) {
      throw new WebSocketConnectException("WebSocket uri is undefined");
    }
    if (webSocketClient == null) {
      synchronized (this) {
        if (webSocketClient == null) {
          WebSocketClientOptions webSocketClientOptions = new WebSocketClientOptions();
          webSocketClientOptions.setUri(options.getRpcURI());
          webSocketClientOptions.setStartTimeOut(options.getRpcStartTimeout());
          webSocketClient = new WebSocketClient(webSocketClientOptions);
        }
      }
    }
    webSocketClient.start();
  }

  public void restartWebSocketClient() {
    webSocketClient.reconnect();
    subscribeAllListener();
  }

  /**
   * Send remote service request
   *
   * @param req Service request
   * @param <T> Service request object type
   */
  public <T> void callService(BaseServiceRequest<T> req) throws IOException, CryptoException {

    if (req == null) {
      throw new IllegalArgumentException("Service request is required");
    }
    req.validateParams();

    String inputJson = JSON.toJSONString(new ServiceMessage<>(req.getHeader(), req.getBody()));

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

    TxOuterClass.Tx tx = this.getTxService().signTx(body, req.getKeyName(), req.getKeyPassword(), false);

    Map<String, String> params = new HashMap<>();
    params.put("tx", Base64.getEncoder().encodeToString(tx.toByteArray()));
    WrappedRequest<Map<String, String>> msg = new WrappedRequest<>(params);
    msg.setMethod("broadcast_tx_sync");
    String res = HttpClient.getInstance().post(options.getRpcURI().toString(), JSON.toJSONString(msg));
    // TODO error handler
    System.out.println(res);
  }

  /**
   * Get key management service
   *
   * @return {@link IKeyService} implementation
   */
  public IKeyService getKeyService() {

    if (this.keyService != null) {
      return this.keyService;
    }

    switch (this.options.getSignAlgo()) {
      case SM2:
        this.keyService = new SM2KeyServiceImpl(this.keyDAO);
        break;
      default:
        this.keyService = new DefaultKeyServiceImpl(this.keyDAO);
    }

    return this.keyService;
  }

  /**
   * Get auth service
   *
   * @return {@link IAuthService} implementation
   */
  public IAuthService getAuthService() {
    if (this.authService != null) {
      return this.authService;
    }
    this.authService = new AuthServiceImpl();
    return this.authService;
  }

  /**
   * Get tx service
   *
   * @return {@link ITxService} implementation
   */
  public ITxService getTxService() {
    if (this.txService != null) {
      return this.txService;
    }

    switch (this.options.getSignAlgo()) {
      case SM2:
        this.txService = new SM2TxServiceImpl();
        break;
      default:
        this.txService = new DefaultTxServiceImpl();
    }

    return this.txService;
  }

  /**
   * Listening from blockchain and notify listeners
   *
   * @param msg Msg from blockchain
   */
  void doNotifyListeners(String msg) {
    this.LISTENERS.forEach(listener -> {
      try {
        listener.callback(msg);
      } catch (CryptoException e) {
        e.printStackTrace();
      }
    });
  }

  void subscribeAllListener() {
    this.LISTENERS.forEach(this::subscribe);
  }

  public void subscribe(AbstractServiceListener listener) {
    WrappedMessage<SubscribeParam> subscribeMessage = SubscribeUtil.buildSubscribeMessage(listener);
    String s = JSON.toJSONString(subscribeMessage);
    System.out.println("subscribe: " + s);
    webSocketClient.send(s);
  }

  public void addListener(AbstractServiceListener listener) {
    this.LISTENERS.add(listener);
  }

  public void setTxOptions(TxOptions options) {
    ITxService txService = getTxService();
    txService.setOptions(options);
  }
}

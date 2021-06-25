package irita.sdk.module.service;

import com.alibaba.fastjson.JSONObject;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.Tx;
import irita.sdk.client.Client;
import irita.sdk.constant.enums.EventEnum;
import irita.sdk.exception.QueryException;
import irita.sdk.model.*;
import irita.sdk.module.base.*;
import irita.sdk.util.AddressUtils;
import irita.sdk.util.PageUtils;
import irita.sdk.util.ResUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceClient extends Client {
    public ServiceClient(Client client) {
        this.nodeUri = client.getNodeUri();
        this.lcd = client.getLcd();
        this.chainId = client.getChainId();
        this.opbOption = client.getOpbOption();
        this.option = client.getOption();
    }

    // DefineService is responsible for creating a new service definition
    public ResultTx defineService(DefineServiceRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());

        Tx.MsgDefineService.Builder builder = Tx.MsgDefineService.newBuilder()
                .setName(req.getServiceName())
                .setDescription(Optional.ofNullable(req.getDescription()).orElse(""))
                .setAuthor(account.getAddress())
                .setAuthorDescription(Optional.ofNullable(req.getAuthorDescription()).orElse(""))
                .setSchemas(req.getSchemas());

        if (req.getTags() != null) {
            builder.addAllTags(req.getTags());
        }
        Tx.MsgDefineService msg = builder.build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    // BindService is responsible for binding a new service definition
    public ResultTx bindService(BindServiceRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());

        CoinOuterClass.Coin coin = CoinOuterClass.Coin.newBuilder()
                .setAmount(req.getDeposit().getAmount())
                .setDenom(req.getDeposit().getDenom())
                .build();

        Tx.MsgBindService msg = Tx.MsgBindService.newBuilder()
                .setServiceName(req.getServiceName())
                .setProvider(account.getAddress())
                .addDeposit(coin)
                .setPricing(req.getPricing())
                .setOptions(req.getOptions())
                .setOwner(account.getAddress())
                .setQos(req.getQoS())
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    // BindService is responsible for binding a new service definition
    public ResultTx updateServiceBinding(UpdateServiceBindingRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        AddressUtils.validAddress(req.getProvider());

        CoinOuterClass.Coin coin = CoinOuterClass.Coin.newBuilder()
                .setAmount(req.getDeposit().getAmount())
                .setDenom(req.getDeposit().getDenom())
                .build();

        Tx.MsgUpdateServiceBinding msg = Tx.MsgUpdateServiceBinding.newBuilder()
                .setServiceName(req.getServiceName())
                .setProvider(req.getProvider())
                .addDeposit(coin)
                .setQos(req.getQoS())
                .setOwner(account.getAddress())
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    // BindService is responsible for binding a new service definition
    public ResultTx disableServiceBinding(String serviceName, String provider, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        AddressUtils.validAddress(provider);

        Tx.MsgDisableServiceBinding msg = Tx.MsgDisableServiceBinding.newBuilder()
                .setServiceName(serviceName)
                .setProvider(provider)
                .setOwner(account.getAddress())
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    // BindService is responsible for binding a new service definition
    public ResultTx enableServiceBinding(String serviceName, String provider, Coin deposit, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        AddressUtils.validAddress(provider);

        CoinOuterClass.Coin coin = CoinOuterClass.Coin.newBuilder()
                .setAmount(deposit.getAmount())
                .setDenom(deposit.getDenom())
                .build();

        Tx.MsgEnableServiceBinding msg = Tx.MsgEnableServiceBinding.newBuilder()
                .setServiceName(serviceName)
                .setProvider(provider)
                .setOwner(account.getAddress())
                .addDeposit(coin)
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    // InvokeService is responsible for invoke a new service
    // return reqCtxId, resultTx
    public CallServiceResp callService(CallServiceRequest req, BaseTx baseTx) throws IOException {
        Account consumer = super.queryAccount(option.getKeyManager().getAddr());
        req.getProviders().forEach(AddressUtils::validAddress);

        CoinOuterClass.Coin coin = CoinOuterClass.Coin.newBuilder()
                .setAmount(req.getServiceFeeCap().getAmount())
                .setDenom(req.getServiceFeeCap().getDenom())
                .build();

        Tx.MsgCallService msg = Tx.MsgCallService.newBuilder()
                .setServiceName(req.getServiceName())
                .addAllProviders(req.getProviders())
                .setConsumer(consumer.getAddress())
                .setInput(req.getInput())
                .addServiceFeeCap(coin)
                .setTimeout(req.getTimeout())
                .setRepeated(req.isRepeated())
                .setRepeatedFrequency(req.getRepeatedFrequency())
                .setRepeatedTotal(req.getRepeatedTotal())
                .build();


        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        ResultTx resultTx = ResUtils.checkAndConvert(res);
        String reqCtxId = resultTx.getEventValue(EventEnum.CREATE_CONTEXT_REQUEST_CONTEXT_ID);
        return new CallServiceResp(reqCtxId, resultTx);
    }


    public ResultTx responseService(ResponseServiceRequest req, BaseTx baseTx) throws IOException, QueryException {
        Account provider = super.queryAccount(option.getKeyManager().getAddr());

        queryServiceRequest(req.getRequestId());

        Tx.MsgRespondService msg = Tx.MsgRespondService.newBuilder()
                .setRequestId(req.getRequestId())
                .setProvider(provider.getAddress())
                .setOutput(req.getOutput())
                .setResult(req.getResult())
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return ResUtils.checkAndConvert(res);
    }

    public ServiceDefinition queryServiceDefinition(String serviceName) throws QueryException {
        String queryServiceDefinitionUri = getQueryUri() + "/irismod/service/definitions/" + serviceName;
        String res = httpUtils().get(queryServiceDefinitionUri);
        QueryServiceDefinitionResponse definitionResponse = JSONObject.parseObject(res, QueryServiceDefinitionResponse.class);

        definitionResponse.valid();
        return definitionResponse.getServiceDefinition();
    }

    public ServiceBinding queryServiceBinding(String serviceName, String provider) throws QueryException {
        String queryServiceBindingUri = getQueryUri() + "/irismod/service/bindings/" + serviceName + "/" + provider;
        String res = httpUtils().get(queryServiceBindingUri);
        QueryServiceBindingResponse bindingResponse = JSONObject.parseObject(res, QueryServiceBindingResponse.class);

        bindingResponse.valid();
        return bindingResponse.getServiceBinding();
    }

    public List<ServiceBinding> queryServiceBindings(String serviceName) throws QueryException {
        return queryServiceBindings(serviceName, null, null);
    }

    public List<ServiceBinding> queryServiceBindings(String serviceName, Integer offset, Integer limit) throws QueryException {
        String baseUri = getQueryUri() + "/irismod/service/bindings/" + serviceName;
        String queryServiceBindingUri = PageUtils.connectUri(baseUri, offset, limit);
        String res = httpUtils().get(queryServiceBindingUri);

        QueryServiceBindingsResponse bindingsResponse = JSONObject.parseObject(res, QueryServiceBindingsResponse.class);
        bindingsResponse.valid();
        return bindingsResponse.getServiceBindings();
    }

    public Request queryServiceRequest(String requestID) throws QueryException {
        String queryServiceRequestUri = getQueryUri() + "/irismod/service/requests/" + requestID;
        String res = httpUtils().get(queryServiceRequestUri);

        QueryServiceRequestResponse serviceRequest = JSONObject.parseObject(res, QueryServiceRequestResponse.class);
        serviceRequest.valid();
        return serviceRequest.getRequest();
    }

    public List<Request> queryServiceRequests(String serviceName, String provider) throws QueryException {
        return queryServiceRequests(serviceName, provider, null, null);
    }

    public List<Request> queryServiceRequests(String serviceName, String provider, Integer offset, Integer limit) throws QueryException {
        String baseUri = getQueryUri() + "/irismod/service/requests/" + serviceName + "/" + provider;
        String queryServiceRequestsUri = PageUtils.connectUri(baseUri, offset, limit);
        String res = httpUtils().get(queryServiceRequestsUri);

        QueryServiceRequestsResponse serviceRequests = JSONObject.parseObject(res, QueryServiceRequestsResponse.class);
        serviceRequests.valid();
        return serviceRequests.getRequests();
    }

    public List<Request> queryRequestsByReqCtx(String reqCtxID, int batchCounter, Integer offset, Integer limit) throws QueryException {
        String baseUri = getQueryUri() + "/irismod/service/requests/" + reqCtxID + "/" + batchCounter;
        String queryServiceRequestsUri = PageUtils.connectUri(baseUri, offset, limit);
        String res = httpUtils().get(queryServiceRequestsUri);

        QueryServiceRequestsResponse serviceRequests = JSONObject.parseObject(res, QueryServiceRequestsResponse.class);
        serviceRequests.valid();
        return serviceRequests.getRequests();
    }


    public Response queryServiceResponse(String requestID) throws QueryException {
        String queryServiceResponseUri = getQueryUri() + "/irismod/service/responses/" + requestID;
        String res = httpUtils().get(queryServiceResponseUri);

        QueryServiceResponseResponse serviceResponse = JSONObject.parseObject(res, QueryServiceResponseResponse.class);
        serviceResponse.valid();
        return serviceResponse.getResponse();
    }

    public List<Response> queryServiceResponses(String reqCtxID, int batchCounter) throws QueryException {
        return queryServiceResponses(reqCtxID, batchCounter, null, null);
    }

    public List<Response> queryServiceResponses(String reqCtxID, int batchCounter, Integer offset, Integer limit) throws QueryException {
        String queryServiceResponsesUri = getQueryUri() + "/irismod/service/responses/" + reqCtxID + "/" + batchCounter;
        String res = httpUtils().get(queryServiceResponsesUri);

        QueryServiceResponsesResponse serviceResponses = JSONObject.parseObject(res, QueryServiceResponsesResponse.class);
        serviceResponses.valid();
        return serviceResponses.getResponses();
    }


    public RequestContext queryRequestContext(String reqCtxID) throws QueryException {
        String queryRequestContextIro = getQueryUri() + "/irismod/service/contexts/" + reqCtxID;
        String res = httpUtils().get(queryRequestContextIro);

        QueryRequestContextResponse requestContextResponse = JSONObject.parseObject(res, QueryRequestContextResponse.class);
        requestContextResponse.valid();
        return requestContextResponse.getRequestContext();
    }

    public List<Msg> subscribeRequest(String serviceName, String provider) {
        return subscribeRequest(serviceName, provider, null, null);
    }

    public List<Msg> subscribeRequest(String serviceName, String provider, Integer minHeight, Integer maxHeight) {
        String baseUri = getQueryUri() + "/txs?create_context.service_name=" + serviceName;
        String subscribeRequestUri = connectMinMaxHeight(baseUri, minHeight, maxHeight);

        String res = httpUtils().get(subscribeRequestUri);
        QueryTxsResponse queryTxsResponse = JSONObject.parseObject(res, QueryTxsResponse.class);

        return getTxMsgsEqualProvider(queryTxsResponse, provider);
    }

    // get All Msgs which provider equal input's provider
    private List<Msg> getTxMsgsEqualProvider(QueryTxsResponse queryTxsResponse, String provider) {
        List<Msg> res = new ArrayList<>();
        List<Txs> txs = queryTxsResponse.getTxs();
        if (txs != null) {
            for (Txs tx : queryTxsResponse.getTxs()) {
                List<Msg> msgs = tx.getTx().getValue().getMsg();
                List<Msg> wantMsgs = msgs.stream().filter(m -> {
                    Optional<String> first = m.getValue().getProviders().stream().filter(p -> p.equals(provider)).findFirst();
                    return first.isPresent();
                }).collect(Collectors.toList());
                res.addAll(wantMsgs);
            }
        }
        return res;
    }

    public List<Txs> subscribeResponse(String serviceName, String provider, String consumer) {
        return subscribeResponse(serviceName, provider, consumer, null, null);
    }


    public List<Txs> subscribeResponse(String serviceName, String provider, String consumer, Integer minHeight, Integer maxHeight) {
        String baseUri = getQueryUri() + "/txs?respond_service.service_name=" + serviceName;
        String subscribeResponseUri = connectProviderConsumer(baseUri, provider, consumer);
        subscribeResponseUri = connectMinMaxHeight(baseUri, minHeight, maxHeight);

        String res = httpUtils().get(subscribeResponseUri);
        QueryTxsResponse queryTxsResponse = JSONObject.parseObject(res, QueryTxsResponse.class);
        return queryTxsResponse.getTxs();
    }

    private String connectProviderConsumer(String uri, String provider, String consumer) {
        if (provider != null) {
            AddressUtils.validAddress(provider);
            uri = "&respond_service.provide=" + provider;
        }
        if (consumer != null) {
            AddressUtils.validAddress(consumer);
            uri = "&respond_service.consumer=" + provider;
        }

        return uri;
    }

    private String connectMinMaxHeight(String uri, Integer minHeight, Integer maxHeight) {
        if (minHeight != null) {
            uri = uri + "&tx.minheight=" + minHeight;
        }
        if (maxHeight != null) {
            uri = uri + "&tx.maxheight=" + maxHeight;
        }

        return uri += "&page=1&limit=200";
    }
}

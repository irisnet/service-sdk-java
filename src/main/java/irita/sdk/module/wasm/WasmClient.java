package irita.sdk.module.wasm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import cosmwasm.wasm.v1beta1.Tx;
import irita.sdk.client.Client;
import irita.sdk.constant.TxStatus;
import irita.sdk.constant.enums.EventEnum;
import irita.sdk.exception.ContractException;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.model.QueryContractInfoResp;
import irita.sdk.model.QueryContractStateResp;
import irita.sdk.module.base.*;
import irita.sdk.util.IOUtils;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WasmClient extends Client {
    public WasmClient(Client client) {
        this.nodeUri = client.getNodeUri();
        this.lcd = client.getLcd();
        this.chainId = client.getChainId();
        this.opbOption = client.getOpbOption();
        this.option = client.getOption();
    }

    // upload the contract to block-chain and return the codeId for user
    public String store(StoreRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());

        if (req.getWasmByteCode() != null) {
            req.setWasmByteCode(req.getWasmByteCode());
        } else {
            byte[] bytes = IOUtils.readAll(req.getWasmFile());
            if (bytes == null) {
                throw new IritaSDKException("file not read");
            }
            req.setWasmByteCode(bytes);
        }

        Tx.MsgStoreCode msg = Tx.MsgStoreCode.newBuilder()
                .setSender(account.getAddress())
                .setWasmByteCode(ByteString.copyFrom(req.getWasmByteCode()))
                .setSource(Optional.ofNullable(req.getSource()).orElse(""))
                .setBuilder(Optional.ofNullable(req.getBuilder()).orElse(""))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        ResultTx resultTx = checkResTxAndConvert(res);

        return resultTx.getEventValue(EventEnum.MESSAGE_CODE_ID);
    }

    // instantiate the contract state
    public String instantiate(InstantiateRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgInstantiateContract.Builder builder = Tx.MsgInstantiateContract.newBuilder()
                .setSender(account.getAddress())
                .setAdmin(Optional.of(req).map(InstantiateRequest::getAdmin).orElse(""))
                .setCodeId(req.getCodeId())
                .setInitMsg(ByteString.copyFrom(JSON.toJSONString(req.getInitMsg()).getBytes(StandardCharsets.UTF_8)))
                .setLabel(req.getLabel());

        if (req.getInitFunds() != null) {
            builder.addInitFunds(
                    CoinOuterClass.Coin.newBuilder()
                            .setDenom(req.getInitFunds().getDenom())
                            .setAmount(req.getInitFunds().getAmount()));
        }

        Tx.MsgInstantiateContract msg = builder.build();
        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        ResultTx resultTx = checkResTxAndConvert(res);

        return resultTx.getEventValue(EventEnum.MESSAGE_CONTRACT_ADDRESS);
    }

    // execute the contract method
    public ResultTx execute(String contractAddress, ContractABI abi, Coin funds, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        byte[] msgBytes = abi.build();

        Tx.MsgExecuteContract.Builder builder = Tx.MsgExecuteContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .setMsg(ByteString.copyFrom(msgBytes));

        if (funds != null) {
            builder.addSentFunds(
                    CoinOuterClass.Coin.newBuilder()
                            .setAmount(funds.getAmount())
                            .setDenom(funds.getDenom()));
        }

        Tx.MsgExecuteContract msg = builder.build();
        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return checkResTxAndConvert(res);
    }

    public ResultTx migrate(String contractAddress, long newCodeID, byte[] msgByte) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgMigrateContract msg = Tx.MsgMigrateContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .setCodeId(newCodeID)
                .setMigrateMsg(ByteString.copyFrom(msgByte))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(null, body, false);
        String res = httpUtils().post(getTxUri(), new WrappedRequest<>(tx));
        return checkResTxAndConvert(res);
    }

    // return the contract information
    public ContractInfo queryContractInfo(String contractAddress) throws ContractException {
        String queryContractInfoUri = getQueryUri() + "/wasm/v1beta1/contract/" + contractAddress;
        String res = httpUtils().get(queryContractInfoUri);
        QueryContractInfoResp contractInfoResp = JSONObject.parseObject(res, QueryContractInfoResp.class);

        if (contractInfoResp.notFound()) {
            throw new ContractException(contractInfoResp.getMessage());
        }
        return contractInfoResp.getContractInfo();
    }

    // execute contract's query method and return the result
    public String queryContract(String address, ContractABI abi) {
        String params = ContractQuery.build(abi.getMethod(), abi.getArgs());
        String encodeParams = URLEncoder.encode(params);

        String baseUri = "/wasm/v1beta1/contract/%s/smart/%s";
        String queryContractUri = String.format(getQueryUri() + baseUri, address, encodeParams);
        return httpUtils().get(queryContractUri);
    }

    // export all state data of the contract
    public Map<String, String> exportContractState(String address) {
        String exportContractStateUri = getQueryUri() + "/wasm/v1beta1/contract/" + address + "/state";
        String res = httpUtils().get(exportContractStateUri);
        QueryContractStateResp contractStateResp = JSONObject.parseObject(res, QueryContractStateResp.class);

        Map<String, String> map = new HashMap<>(contractStateResp.getModels().size());
        for (QueryContractStateResp.Models model : contractStateResp.getModels()) {
            byte[] bytes = Hex.decode(model.getKey());
            final int PREFIX = 2;
            byte[] dest = new byte[bytes.length - PREFIX];
            System.arraycopy(bytes, PREFIX, dest, 0, dest.length);

            String key = new String(dest);
            String value = new String(Base64.getDecoder().decode(model.getValue()));
            map.put(key, value);
        }
        return map;
    }

    // TODO client do this
    private ResultTx checkResTxAndConvert(String res) {
        ResultTx resultTx = JSON.parseObject(res, ResultTx.class);

        if (resultTx.getCode() != TxStatus.SUCCESS) {
            throw new IritaSDKException(resultTx.getLog());
        }
        return resultTx;
    }
}

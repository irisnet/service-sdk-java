package irita.sdk.client;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.crypto.sm2.Keys;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import irita.opb.OpbOption;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.model.QueryAccountResp;
import irita.sdk.module.base.Account;
import irita.sdk.module.base.BaseTx;
import irita.sdk.module.base.TxService;
import irita.sdk.module.keys.Key;
import irita.sdk.util.ByteUtils;
import irita.sdk.util.SM2Utils;
import irita.sdk.util.http.BlockChainHttp;
import irita.sdk.util.http.CommonHttpUtils;
import irita.sdk.util.http.OpbProjectKeyHttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;

public abstract class Client implements TxService {
    public IritaClientOption option;
    protected OpbOption opbOption;
    protected String nodeUri;
    protected String lcd;
    protected String chainId;

    @Override
    public TxOuterClass.Tx signTx(BaseTx base, TxOuterClass.TxBody txBody, boolean offline) {
        BaseTx baseTx = initBaseTx(base);

        Key km = option.getKeyManager();
        BigInteger privKey = km.getPrivKey();
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);
        byte[] publicKeyEncoded = publicKey.getEncoded(true);

        Account account = queryAccount(km.getAddr());
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Any.pack(Keys.PubKey.newBuilder().setKey(ByteString.copyFrom(publicKeyEncoded)).build(), "/"))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(account.getSequence()))
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(baseTx.getGas()).addAmount(CoinOuterClass.Coin.newBuilder().setAmount(baseTx.getFee().amount).setDenom(baseTx.getFee().denom))).build();


        TxOuterClass.SignDoc signDoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(account.getAccountNumber())
                .setChainId(chainId)
                .build();

        byte[] signature;
        BigInteger[] rs;
        try {
            signature = SM2Utils.sign(privKey, signDoc.toByteArray());
            rs = SM2Utils.getRSFromSignature(signature);
        } catch (CryptoException | IOException e) {
            throw new IritaSDKException("use sm2 sign filed", e);
        }
        byte[] sigBytes = ByteUtils.addAll(ByteUtils.toBytesPadded(rs[0], 32), ByteUtils.toBytesPadded(rs[1], 32));

        return TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();
    }

    private BaseTx initBaseTx(BaseTx baseTx) {
        if (baseTx != null) {
            return baseTx;
        }

        baseTx = new BaseTx(option.getGas(), option.getFee());
        return baseTx;
    }

    // if you want to add memo, you will build by yourSelf
    public TxOuterClass.TxBody buildTxBody(com.google.protobuf.GeneratedMessageV3 msg) {
        return TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msg, "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();
    }

    public Account queryAccount(String address) {
        String queryAccountUrl = getQueryAccountUrl(address);
        String res = httpUtils().get(queryAccountUrl);
        QueryAccountResp baseAccount = JSONObject.parseObject(res, QueryAccountResp.class);

        if (baseAccount.notFound()) {
            throw new IritaSDKException("account:\t" + address + " is not exist");
        }

        Account account = new Account();
        account.setAddress(baseAccount.getAddress());
        account.setAccountNumber(baseAccount.getAccountNumber());
        account.setSequence(baseAccount.getSequence());
        return account;
    }

    // different between when enable ProjectKey
    protected BlockChainHttp httpUtils() {
        switch (opbOption.getOpbEnum()) {
            case ENABLE:
                if (StringUtils.isNotEmpty(opbOption.getProjectKey())) {
                    return new OpbProjectKeyHttpUtils(opbOption.getProjectKey());
                }
            case DISABLE:
                return new CommonHttpUtils();
            default:
                throw new IritaSDKException("opbOption incorrect");
        }
    }

    private String getQueryAccountUrl(String address) {
        return getQueryUri() + "/auth/accounts/" + address;
    }

    protected String getQueryUri() {
        switch (opbOption.getOpbEnum()) {
            case ENABLE:
                return opbOption.getOpbRestUri();
            case DISABLE:
                return lcd;
            default:
                throw new IritaSDKException("opbOption incorrect");
        }
    }

    protected String getTxUri() {
        switch (opbOption.getOpbEnum()) {
            case ENABLE:
                return opbOption.getOpbRpcUri();
            case DISABLE:
                return nodeUri;
            default:
                throw new IritaSDKException("opbOption incorrect");
        }
    }

    public IritaClientOption getOption() {
        return option;
    }

    public OpbOption getOpbOption() {
        return opbOption;
    }

    public String getNodeUri() {
        return nodeUri;
    }

    public String getLcd() {
        return lcd;
    }

    public String getChainId() {
        return chainId;
    }
}

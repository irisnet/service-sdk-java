package iservice.sdk.module.impl;

import com.google.protobuf.ByteString;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Key;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IAuthService;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.ITxService;
import iservice.sdk.util.SM2Utils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.crypto.CryptoException;
import org.web3j.utils.Numeric;

/**
 * @author Yelong
 */
public class SM2TxServiceImpl implements ITxService {

    private IKeyService keyService;
    private IAuthService authService;

    public SM2TxServiceImpl() {
        ServiceClient serviceClient = ServiceClientFactory.getInstance().getClient();

        this.keyService = serviceClient.getKeyService();
        this.authService = serviceClient.getAuthService();
    }

    @Override
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException, CryptoException {
        Key key = this.keyService.getKey(name, password);

        Auth.BaseAccount baseAccount = this.authService.queryAccount(key.getAddress());
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                //.setPublicKey(Crypto.PublicKey.newBuilder().setAnyPubkey(Any.newBuilder().setUnknownFields(ByteString.copyFrom(encodedPubkey))))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(baseAccount.getSequence()))

                // TODO : Configurable
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("point"))).build();

        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(baseAccount.getAccountNumber())
                .setChainId("irita")
                .build();

        BigInteger privkey = Numeric.toBigInt(key.getPrivKey());

        SM2Utils sm2Utils = new SM2Utils();
        byte[] signature = sm2Utils.sign(privkey, signdoc.toByteArray());

        BigInteger[] rs = sm2Utils.getRSFromSignature(signature);
        byte[] sigBytes = ArrayUtils.addAll(Numeric.toBytesPadded(rs[0], 32), Numeric.toBytesPadded(rs[1], 32));

        TxOuterClass.Tx tx = TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();

        return tx;
    }
}

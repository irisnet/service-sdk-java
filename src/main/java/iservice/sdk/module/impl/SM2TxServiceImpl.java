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
import iservice.sdk.util.SM2Utils.SM2KeyPair;
import iservice.sdk.util.SM2Utils.SM2.Signature;
import iservice.sdk.util.SM2Utils.SM2;
import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.Sha256Hash;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;

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
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException {

        Key key = this.keyService.getKey(name, password);
        BigInteger privKey = new BigInteger(key.getPrivKey());
        SM2 sm2 = new SM2();
        ECPoint pubKey = sm2.getPubKeyFromPrivKey(privKey);
        SM2KeyPair sm2KeyPair = new SM2KeyPair(pubKey, privKey);

        Auth.BaseAccount baseAccount = this.authService.queryAccount(key.getAddress());
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(baseAccount.getSequence()))

                // TODO : Configurable
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("stake"))).build();

        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(baseAccount.getAccountNumber())
                .setChainId("irita")
                .build();

        byte[] hash = Sha256Hash.hash(signdoc.toByteArray());
        Signature signature = sm2.sign(new String(hash), key.getAddress(), sm2KeyPair);
        byte[] sigBytes = ArrayUtils.addAll(signature.getR().toByteArray(), signature.getS().toByteArray());

        TxOuterClass.Tx tx = TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();

        return tx;
    }
}

package iservice.sdk.module.impl;

import com.google.protobuf.ByteString;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.crypto.v1beta1.Crypto;
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
import org.bitcoinj.core.Sha256Hash;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.io.IOException;

/**
 * @author Yelong
 */
public class TxServiceImpl implements ITxService {

    private IKeyService keyService;
    private IAuthService authService;

    public TxServiceImpl() {
        ServiceClient serviceClient = ServiceClientFactory.getInstance().getClient();

        this.keyService = serviceClient.getKeyService();
        this.authService = serviceClient.getAuthService();
    }

    @Override
    public TxOuterClass.TxRaw signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException {

        Key key = this.keyService.getKey(name, password);
        ECKeyPair keyPair = ECKeyPair.create(key.getPrivKey());

        Auth.BaseAccount baseAccount = this.authService.queryAccount(key.getAddress());
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Crypto.PublicKey.newBuilder().setSecp256K1(ByteString.copyFrom(keyPair.getPublicKey().toByteArray())))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(baseAccount.getSequence()))

                // TODO: Configurable
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("1").setDenom("stake"))).build();

        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(baseAccount.getAccountNumber())
                .setChainId("test")
                .build();

        byte[] hash = Sha256Hash.hash(signdoc.toByteArray());
        Sign.SignatureData signature = Sign.signMessage(hash, keyPair, false);
        String r = Numeric.toHexString(signature.getR());
        String s = Numeric.toHexString(signature.getS());
        String sig = Numeric.cleanHexPrefix(r) + Numeric.cleanHexPrefix(s);

        TxOuterClass.TxRaw txRaw = TxOuterClass.TxRaw.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .addSignatures(ByteString.copyFrom(sig.getBytes()))
                .build();
        return txRaw;
    }
}

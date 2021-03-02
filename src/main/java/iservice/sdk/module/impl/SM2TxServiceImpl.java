package iservice.sdk.module.impl;

import com.google.protobuf.Any;
import cosmos.crypto.sm2.Keys;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

import com.google.protobuf.ByteString;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Key;
import iservice.sdk.entity.SignAlgo;
import iservice.sdk.entity.options.ServiceClientOptions;
import iservice.sdk.entity.options.TxOptions;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IAuthService;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.ITxService;
import iservice.sdk.util.SM2Utils;

public class SM2TxServiceImpl implements ITxService {

  private TxOptions options;

  private final IKeyService keyService;
  private final IAuthService authService;

  public SM2TxServiceImpl() {
    ServiceClientOptions options = new ServiceClientOptions();
    options.setSignAlgo(SignAlgo.SM2);
    ServiceClient serviceClient = ServiceClientFactory.getInstance().setOptions(options).getClient();
    this.keyService = serviceClient.getKeyService();
    this.authService = serviceClient.getAuthService();
  }

  @Override
  public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException, CryptoException {
    Key key = this.keyService.getKey(name, password);

    BigInteger privKey = new BigInteger(1, key.getPrivKey());
    ECPoint pubkey = SM2Utils.getPublicKeyFromPrivkey(privKey);

    byte[] encodedPubkey = pubkey.getEncoded(true);

    Auth.BaseAccount baseAccount = this.authService.queryAccount(key.getAddress());
    TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
      .addSignerInfos(
        TxOuterClass.SignerInfo.newBuilder()
          .setPublicKey(Any.pack(Keys.PubKey.newBuilder().setKey(ByteString.copyFrom(encodedPubkey)).build(), "/"))
          .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
          .setSequence(baseAccount.getSequence()))
          .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(this.options.gasLimit).addAmount(CoinOuterClass.Coin.newBuilder().setAmount(this.options.fee.amount).setDenom(this.options.fee.denom))).build();

    TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
      .setBodyBytes(txBody.toByteString())
      .setAuthInfoBytes(ai.toByteString())
      .setAccountNumber(baseAccount.getAccountNumber())
      .setChainId(this.options.chainID)
      .build();

    byte[] signature = SM2Utils.sign(privKey, signdoc.toByteArray());

    BigInteger[] rs = SM2Utils.getRSFromSignature(signature);
    byte[] sigBytes = ArrayUtils.addAll(Numeric.toBytesPadded(rs[0], 32), Numeric.toBytesPadded(rs[1], 32));

    return TxOuterClass.Tx.newBuilder()
      .setBody(txBody)
      .setAuthInfo(ai)
      .addSignatures(ByteString.copyFrom(sigBytes))
      .build();
  }

  @Override
  public void setOptions(TxOptions options) {
    this.options = options;
  }

  public TxOptions getOptions() {
    return options;
  }
}

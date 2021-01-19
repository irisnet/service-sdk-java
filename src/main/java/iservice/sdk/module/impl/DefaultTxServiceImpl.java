package iservice.sdk.module.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.io.IOException;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.crypto.secp256k1.Keys;
import cosmos.auth.v1beta1.Auth;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;

import iservice.sdk.entity.options.TxOptions;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Key;
import iservice.sdk.exception.ServiceSDKException;
import iservice.sdk.module.IAuthService;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.ITxService;

/**
 * @author Yelong
 */
public class DefaultTxServiceImpl implements ITxService {

  private TxOptions options;

  private IKeyService keyService;
  private IAuthService authService;

  public DefaultTxServiceImpl() {
    ServiceClient serviceClient = ServiceClientFactory.getInstance().getClient();
    this.keyService = serviceClient.getKeyService();
    this.authService = serviceClient.getAuthService();
  }

  @Override
  public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, String name, String password, boolean offline) throws ServiceSDKException, IOException {

    Key key = this.keyService.getKey(name, password);
    ECKeyPair keyPair = ECKeyPair.create(key.getPrivKey());

    byte[] encodedPubkey = ECKey.publicPointFromPrivate(keyPair.getPrivateKey()).getEncoded(true);

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

    byte[] hash = Sha256Hash.hash(signdoc.toByteArray());
    Sign.SignatureData signature = Sign.signMessage(hash, keyPair, false);
    byte[] sigBytes = ArrayUtils.addAll(signature.getR(), signature.getS());

    TxOuterClass.Tx tx = TxOuterClass.Tx.newBuilder()
      .setBody(txBody)
      .setAuthInfo(ai)
      .addSignatures(ByteString.copyFrom(sigBytes))
      .build();

    return tx;
  }

  @Override
  public void setOptions(TxOptions options) {
    this.options = options;
  }

  public TxOptions getOptions() {
    return options;
  }
}

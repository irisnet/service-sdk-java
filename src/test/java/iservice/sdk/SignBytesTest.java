package iservice.sdk;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import cosmos.base.crypto.v1beta1.Crypto;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.Service;
import iservice.sdk.util.Bech32Utils;

import java.util.Base64;

/**
 * @author Yelong
 */
public class SignBytesTest {

    public static void main(String[] args) throws Exception {

        Service.MsgCallService msg = Service.MsgCallService.newBuilder()
                .addProviders(ByteString.copyFrom(Bech32Utils.fromBech32("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht")))
                .setConsumer(ByteString.copyFrom(Bech32Utils.fromBech32("iaa10pxgfknjvx48at0gwmuzql5fkce406vyv0emxx")))
                .setServiceName("test")
                .setInput("{\"header\":{\"version\":\"1.0\",\"location\":{\"status\":\"off\"}},\"body\":{\"id\":\"1\",\"name\":\"irisnet\",\"data\":\"facedata\"}}")
                .addServiceFeeCap(CoinOuterClass.Coin.newBuilder().setAmount("1").setDenom("stake"))
                .setTimeout(100)
                .setSuperMode(false)
                .setRepeated(false)
                .setRepeatedFrequency(0)
                .setRepeatedTotal(0)
                .build();

        TxOuterClass.TxBody body = TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msg, "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();

        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Crypto.PublicKey.newBuilder().setSecp256K1(ByteString.copyFrom(Base64.getDecoder().decode("A3V1YDPI6yFMv+SQBwCMjxMVjpEebXOWSck9kzJW+vEG"))))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(0))
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("1").setDenom("stake"))).build();

        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(body.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(9)
                .setChainId("test")
                .build();

        byte[] signbytes = signdoc.toByteArray();

        System.out.println(new String(signbytes));
    }
}

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
                .setConsumer(ByteString.copyFrom(Bech32Utils.fromBech32("iaa176l662tt6e3uqxu57hdxpupk972gw0y8j4aa0a")))
                .setServiceName("test")
                .setInput("{\"header\":{\"version\":\"1.0\",\"location\":{\"status\":\"off\"}},\"body\":{\"id\":\"1\",\"name\":\"irisnet\",\"data\":\"facedata\"}}")
                .addServiceFeeCap(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("point"))
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

        byte[] pk = ByteString.copyFrom(Base64.getDecoder().decode("A6kp7WGG1dYQoWkH1jb7bPifDEWDZTykDDjyeeK3DJJ3")).toByteArray();
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Crypto.PublicKey.newBuilder().setSecp256K1(ByteString.copyFrom(Base64.getDecoder().decode("A6kp7WGG1dYQoWkH1jb7bPifDEWDZTykDDjyeeK3DJJ3"))))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(0))
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(200000).addAmount(CoinOuterClass.Coin.newBuilder().setAmount("10").setDenom("point"))).build();

        TxOuterClass.SignDoc signdoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(body.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(10)
                .setChainId("test")
                .build();

        byte[] signbytes = signdoc.toByteArray();

        System.out.println(new String(signbytes));
    }
}

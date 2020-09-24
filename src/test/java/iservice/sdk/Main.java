package iservice.sdk;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.googlecode.protobuf.format.JsonFormat;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import irismod.service.Service;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.util.Bech32Utils;

import java.net.URI;
import java.util.Base64;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {
////        IAuthService s = new AuthServiceImpl();
////        s.queryAccount("iaa10pxgfknjvx48at0gwmuzql5fkce406vyv0emxx");
//
//        ManagedChannelBuilder<?> o = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext();
//        ManagedChannel channel = o.build();
//
//        QueryGrpc.QueryBlockingStub queryBlockingStub = QueryGrpc.newBlockingStub(channel);
//        QueryOuterClass.QueryAccountResponse res = queryBlockingStub.account(QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(ByteString.copyFrom(Bech32Utils.fromBech32("iaa10pxgfknjvx48at0gwmuzql5fkce406vyv0emxx"))).build());
//        Auth.BaseAccount baseAccount = Auth.BaseAccount.parseFrom(res.getAccount().getValue().toByteArray());
//        System.out.println(Bech32Utils.toBech32("iaa", baseAccount.getAddress().toByteArray()));
//        System.out.println(baseAccount.getSequence());
//        System.out.println(baseAccount.getAccountNumber());

        ServiceClientOptions options = new ServiceClientOptions();
        options.setGrpcURI(new URI("localhost:9090"));
        ServiceClient client = ServiceClientFactory.getInstance().setOptions(options).getClient();
        String address = client.getKeyService().recoverKey("test", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");
        System.out.println("Address: " + address);
        Service.MsgCallService msg = Service.MsgCallService.newBuilder()
                .addProviders(ByteString.copyFrom(Bech32Utils.fromBech32("iaa1ewed0ds2syhv4qn6fjhx2avma0j2sp6d594tht")))
                .setConsumer(ByteString.copyFrom(Bech32Utils.fromBech32("iaa176l662tt6e3uqxu57hdxpupk972gw0y8j4aa0a")))
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

        TxOuterClass.Tx tx = client.getTxService().signTx(body, "test", "123456", false);

        byte[] txBytes = tx.toByteArray();
//        BroadcastAPIGrpc.BroadcastAPIBlockingStub stub = BroadcastAPIGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
//        Types.ResponseBroadcastTx responseBroadcastTx = stub.broadcastTx(Types.RequestBroadcastTx.newBuilder().setTx(txRaw.getBodyBytes()).build());
//        System.out.println(responseBroadcastTx.toString());
        JsonFormat jsonFormat = new JsonFormat();
//        System.out.println(new String(txRaw.toByteArray()));
//        System.out.println(jsonFormat.printToString(txRaw));
        System.out.println(new String(Base64.getEncoder().encode(tx.toByteArray())));
    }
}

package iservice.sdk;

import com.google.protobuf.ByteString;
import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.auth.v1beta1.QueryOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import iservice.sdk.util.Bech32Utils;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        IAuthService s = new AuthServiceImpl();
//        s.queryAccount("iaa10pxgfknjvx48at0gwmuzql5fkce406vyv0emxx");

        ManagedChannelBuilder<?> o = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext();
        ManagedChannel channel = o.build();

        QueryGrpc.QueryBlockingStub queryBlockingStub = QueryGrpc.newBlockingStub(channel);
        QueryOuterClass.QueryAccountResponse res = queryBlockingStub.account(QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(ByteString.copyFrom(Bech32Utils.fromBech32("iaa10pxgfknjvx48at0gwmuzql5fkce406vyv0emxx"))).build());
        Auth.BaseAccount baseAccount = Auth.BaseAccount.parseFrom(res.getAccount().getValue().toByteArray());
        System.out.println(Bech32Utils.toBech32("iaa", baseAccount.getAddress().toByteArray()));
        System.out.println(baseAccount.getSequence());
        System.out.println(baseAccount.getAccountNumber());
    }
}

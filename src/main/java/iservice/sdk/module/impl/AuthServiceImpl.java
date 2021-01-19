package iservice.sdk.module.impl;

import com.google.protobuf.ByteString;
import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.auth.v1beta1.QueryOuterClass;
import iservice.sdk.module.IAuthService;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.util.Bech32Utils;

import java.io.IOException;

/**
 * @author Yelong
 */
public class AuthServiceImpl implements IAuthService {

    private QueryGrpc.QueryBlockingStub queryBlockingStub;

    public AuthServiceImpl() {

        this.queryBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());

    }

    @Override
    public Auth.BaseAccount queryAccount(String address) throws IOException {

        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(address).build();

        QueryOuterClass.QueryAccountResponse res = this.queryBlockingStub.account(req);
        Auth.BaseAccount baseAccount = Auth.BaseAccount.parseFrom(res.getAccount().getValue().toByteArray());
//        System.out.println(Bech32Utils.toBech32("iaa", baseAccount.getAddress().toByteArray()));
        return baseAccount;
    }
}

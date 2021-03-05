package iservice.sdk.module.impl;

import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.auth.v1beta1.QueryOuterClass;
import iservice.sdk.module.IAuthService;
import iservice.sdk.net.GrpcChannel;

import java.io.IOException;

/**
 * @author Yelong
 */
public class AuthServiceImpl implements IAuthService {

    private final QueryGrpc.QueryBlockingStub queryBlockingStub;

    public AuthServiceImpl() {

        this.queryBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());

    }

    @Override
    public Auth.BaseAccount queryAccount(String address) throws IOException {

        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(address).build();

        QueryOuterClass.QueryAccountResponse res = this.queryBlockingStub.account(req);
        return Auth.BaseAccount.parseFrom(res.getAccount().getValue().toByteArray());
    }
}

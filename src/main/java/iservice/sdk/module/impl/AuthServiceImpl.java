package iservice.sdk.module.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.googlecode.protobuf.format.JsonFormat;
import com.squareup.okhttp.*;
import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.auth.v1beta1.QueryOuterClass;
import iservice.sdk.entity.WrappedRequest;
import iservice.sdk.entity.WrappedResponse;
import iservice.sdk.module.IAuthService;
import iservice.sdk.net.GrpcChannel;
import iservice.sdk.util.Bech32Utils;
import org.web3j.utils.Numeric;
import tendermint.abci.Types;

import java.io.IOException;
import java.util.Map;

/**
 * @author Yelong
 */
public class AuthServiceImpl implements IAuthService {

    private QueryGrpc.QueryBlockingStub queryBlockingStub;

    public AuthServiceImpl() {

        this.queryBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.INSTANCE.getChannel());

    }

    @Override
    public Auth.BaseAccount queryAccount(String address) throws IOException {

        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(ByteString.copyFrom(Bech32Utils.fromBech32(address))).build();

        QueryOuterClass.QueryAccountResponse account = this.queryBlockingStub.account(req);
        System.out.println(account.toString());
        return null;
    }
}

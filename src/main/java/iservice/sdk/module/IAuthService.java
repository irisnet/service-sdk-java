package iservice.sdk.module;

import cosmos.auth.v1beta1.Auth;

import java.io.IOException;

/**
 * @author Yelong
 */
public interface IAuthService {

    Auth.BaseAccount queryAccount(String address) throws IOException;
}

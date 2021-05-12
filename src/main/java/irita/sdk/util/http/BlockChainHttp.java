package irita.sdk.util.http;


import irita.sdk.module.base.WrappedRequest;

import java.io.IOException;

// this httpUtils just for connect with block chain
public interface BlockChainHttp {
    String get(String uri);


    /**
     * common post, content-type: application/json
     *
     * @param uri
     * @param body json
     * @return res
     */
    String post(String uri, String body) throws IOException;

    <S extends com.google.protobuf.GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException;

}

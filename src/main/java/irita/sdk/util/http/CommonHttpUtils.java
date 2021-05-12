package irita.sdk.util.http;


import com.alibaba.fastjson.JSON;
import irita.sdk.module.base.WrappedRequest;
import irita.sdk.util.HttpUtils;

import java.io.IOException;

public class CommonHttpUtils implements BlockChainHttp {

    // this get just for call lcd, if you want use a common get, you will need refactor
    @Override
    public String get(String uri) {
        return HttpUtils.get(uri);
    }


    /**
     * common post, content-type: application/json
     *
     * @param uri
     * @param body json
     * @return res
     */
    @Override
    public String post(String uri, String body) throws IOException {
        return HttpUtils.post(uri, body);
    }

    // use this to send tx
    @Override
    public <S extends com.google.protobuf.GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException {
        return post(uri, JSON.toJSONString(object));
    }
}

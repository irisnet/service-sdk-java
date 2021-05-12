package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.RequestContext;

public class QueryRequestContextResponse extends QueryErrResponse {
    @JSONField(name = "request_context")
    private RequestContext requestContext;

    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }
}
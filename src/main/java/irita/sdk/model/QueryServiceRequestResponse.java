package irita.sdk.model;

import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.Request;

public class QueryServiceRequestResponse extends QueryErrResponse {
    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
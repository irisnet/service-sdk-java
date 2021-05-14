package irita.sdk.model;

import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.Response;

public class QueryServiceResponseResponse extends QueryErrResponse {
    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
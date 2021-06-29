package irita.sdk.model;

import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.Request;
import irita.sdk.module.service.RequestByContext;

import java.util.List;

public class QueryServiceRequestsResponseByContext extends QueryErrResponse {
    private String height;
    private List<RequestByContext> result;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<RequestByContext> getResult() {
        return result;
    }

    public void setResult(List<RequestByContext> result) {
        this.result = result;
    }
}
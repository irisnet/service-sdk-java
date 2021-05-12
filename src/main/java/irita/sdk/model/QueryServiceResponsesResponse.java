package irita.sdk.model;

import irita.sdk.module.base.Pagination;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.Response;

import java.util.List;

public class QueryServiceResponsesResponse extends QueryErrResponse {
    private List<Response> responses;
    private Pagination pagination;

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
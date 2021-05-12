package irita.sdk.model;

import irita.sdk.module.base.Pagination;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.Request;

import java.util.List;

public class QueryServiceRequestsResponse extends QueryErrResponse {
    private List<Request> requests;
    private Pagination pagination;

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
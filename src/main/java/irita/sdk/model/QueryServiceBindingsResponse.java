package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.Pagination;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.ServiceBinding;

import java.util.List;

public class QueryServiceBindingsResponse extends QueryErrResponse {
    @JSONField(name = "service_bindings")
    private List<ServiceBinding> serviceBindings;
    private Pagination pagination;

    public void setServiceBindings(List<ServiceBinding> serviceBindings) {
        this.serviceBindings = serviceBindings;
    }

    public List<ServiceBinding> getServiceBindings() {
        return serviceBindings;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
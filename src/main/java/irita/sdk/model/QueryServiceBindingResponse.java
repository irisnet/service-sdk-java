package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.ServiceBinding;

public class QueryServiceBindingResponse extends QueryErrResponse {
    @JSONField(name = "service_binding")
    private ServiceBinding serviceBinding;

    public void setServiceBinding(ServiceBinding serviceBinding) {
        this.serviceBinding = serviceBinding;
    }

    public ServiceBinding getServiceBinding() {
        return serviceBinding;
    }
}
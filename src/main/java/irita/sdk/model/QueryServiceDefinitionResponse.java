package irita.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import irita.sdk.module.base.QueryErrResponse;
import irita.sdk.module.service.ServiceDefinition;

public class QueryServiceDefinitionResponse extends QueryErrResponse {
    @JSONField(name = "service_definition")
    private ServiceDefinition serviceDefinition;

    public void setServiceDefinition(ServiceDefinition serviceDefinition) {
        this.serviceDefinition = serviceDefinition;
    }

    public ServiceDefinition getServiceDefinition() {
        return serviceDefinition;
    }
}

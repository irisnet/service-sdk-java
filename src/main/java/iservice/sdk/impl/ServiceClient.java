package iservice.sdk.impl;

import iservice.sdk.entity.BaseServiceRequest;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.module.IKeyDAO;
import iservice.sdk.module.IKeyService;
import iservice.sdk.module.impl.DefaultKeyServiceImpl;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry of service client
 *
 * @author Yelong
 */
public class ServiceClient {

    private ServiceClientOptions options;
    private List<AbstractServiceListener> listeners;
    private IKeyDAO keyDAO;

    public ServiceClient(ServiceClientOptions options, List<AbstractServiceListener> listeners, IKeyDAO keyDAO) {
        this.options = options;
        this.listeners = listeners != null ? listeners : new ArrayList<>();
        this.keyDAO = keyDAO;
    }

    public void callService(BaseServiceRequest req) {
        // TODO 创建 tx 并签名广播
        System.out.println("Sending request ...");
        System.out.println(req.toString());
    }

    public IKeyService getKeyService() {
        switch (this.options.getSignAlgo()) {
            case SM2:
                throw new NotImplementedException("SM2 not implemented");
            default:
                return new DefaultKeyServiceImpl(this.keyDAO);
        }
    }


}

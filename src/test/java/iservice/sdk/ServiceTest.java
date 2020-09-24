package iservice.sdk;

import irismod.service.QueryGrpc;
import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;
import iservice.sdk.net.GrpcChannel;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Yelong
 */
public class ServiceTest {
    public static void main(String[] args) throws URISyntaxException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setUri(new URI("localhost:9090"));
        ServiceClient client = ServiceClientFactory.getInstance().setOptions(options).getClient();
        QueryGrpc.QueryBlockingStub queryBlockingStub = QueryGrpc.newBlockingStub(GrpcChannel.getInstance().getChannel());
//        queryBlockingStub.
    }
}

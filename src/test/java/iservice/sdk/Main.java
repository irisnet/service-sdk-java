package iservice.sdk;

import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.Key;
import iservice.sdk.entity.options.ServiceClientOptions;
import iservice.sdk.module.IKeyService;

import java.net.URI;

/**
 * Created by mitch on 2020/9/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setRpcURI(new URI("http://localhost:26657/websocket"));
        options.setGrpcURI(new URI("http://localhost:9090"));
        IKeyService keyService = ServiceClientFactory.getInstance().setOptions(options).getClient().getKeyService();
        String address = keyService.recoverKey("test1", "123456", "help such mushroom spell cream cattle cute brush crucial boat flat system oxygen apart sock leave position jaguar winner violin manage exchange scissors employ", true, 0, "");
        System.out.println(address);
        Key key = keyService.getKey("test1", "123456");
    }
}

package com.example.demo;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Yelong
 */
public class ServiceTest {

    public static void main(String[] args) throws URISyntaxException, IOException {
        ServiceClientOptions options = new ServiceClientOptions();
        options.setGrpcURI(new URI("http://10.1.4.99:9090"));
        options.setRpcURI(new URI("http://10.1.4.99:26657"));
        ServiceClient client = ServiceClientFactory.getInstance().setOptions(options).getClient();
        client.getKeyService().recoverKey("consumer", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");

        client.callService(new TestServiceRequest());
    }

}

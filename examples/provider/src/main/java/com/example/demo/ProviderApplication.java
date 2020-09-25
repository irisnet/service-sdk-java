package com.example.demo;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class ProviderApplication {

	public static void main(String[] args) throws URISyntaxException {

		ServiceClientOptions options = new ServiceClientOptions();
		options.setRpcURI(new URI("http://10.1.4.99:26657"));
		options.setGrpcURI(new URI("http://10.1.4.99:9090"));
		ServiceClient client = ServiceClientFactory.getInstance()
				.setOptions(options)
				.addListener(new TestProviderListener())
				.getClient();
		client.getKeyService().recoverKey("provider", "123456", "help such mushroom spell cream cattle cute brush crucial boat flat system oxygen apart sock leave position jaguar winner violin manage exchange scissors employ", true, 0, "");
		client.start();
		SpringApplication.run(ProviderApplication.class, args);
	}

}

package com.example.demo;

import iservice.sdk.core.ServiceClient;
import iservice.sdk.core.ServiceClientFactory;
import iservice.sdk.entity.ServiceClientOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) throws URISyntaxException {

		ServiceClientOptions options = new ServiceClientOptions();
		options.setRpcURI(new URI("http://10.1.4.99:26657"));
		options.setGrpcURI(new URI("http://10.1.4.99:9090"));
		ServiceClient client = ServiceClientFactory.getInstance()
				.setOptions(options)
				.addListener(new TestConsumerListener())
				.getClient();
		client.getKeyService().recoverKey("consumer", "123456", "potato below health analyst hurry arrange shift tent elevator syrup clever ladder adjust agree dentist pass best space behind badge enemy nothing twice nut", true, 0, "");
		client.start();

		SpringApplication.run(ConsumerApplication.class, args);
	}

}

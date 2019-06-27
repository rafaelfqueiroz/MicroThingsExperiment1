package com.microthingsexperiment.iotgateway;

import java.util.List;

import org.eclipse.californium.core.CoapResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages="com.microthingsexperiment")
public class IoTGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTGatewayApplication.class, args);
	}

	@Bean
	@Profile("coap")
	public CoapConfigurationExecution coapConfigurationExecution(List<CoapResource> resources, Environment env) {
		return new CoapConfigurationExecution(resources, env.getProperty("server.port", Integer.class));
	}
	
}

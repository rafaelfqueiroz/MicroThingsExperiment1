package com.microthingsexperiment.iotgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication(scanBasePackages="com.microthingsexperiment")
@EnableCircuitBreaker
public class IoTGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTGatewayApplication.class, args);
	}

}

package com.microthingsexperiment.iotgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.microthingsexperiment")
public class IoTGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTGatewayApplication.class, args);
	}

}

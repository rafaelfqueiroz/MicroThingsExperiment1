package com.microthingsexperiment.caller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.ActiveProfiles;
import com.microthingsexperiment.caller.Setup;
import com.microthingsexperiment.caller.service.registration.Device;

@Component
public class SetupService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ServiceRegistry serviceRegistry;
	
	@Autowired
	private Setup setup;
	@Autowired
	private ActiveProfiles profiles;
	
	public void initializeSetup() {
		setup.activate();
		
		if (profiles.isProfileActive("gatewayRequest")) {
			restTemplate.getForObject(
					new StringBuilder("http://")
									.append(serviceRegistry.getGateway().getHost())
									.append(":")
									.append(serviceRegistry.getGateway().getPort())
									.append("/gateway/setup").toString(), String.class);
		}
		
		
		for (Device device : serviceRegistry.getDevices()) {
			restTemplate.getForObject(new StringBuilder("http://")
					.append(device.getHost())
					.append(":")
					.append(device.getPort())
					.append("/device/setup").toString(), String.class);
		}
	}

}

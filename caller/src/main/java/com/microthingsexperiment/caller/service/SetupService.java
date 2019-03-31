package com.microthingsexperiment.caller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.caller.Setup;

@Component
public class SetupService {
	
	@Autowired
	private RestTemplate restTemplate;
	@Value("${request.host}")
	private String host;
	@Value("${request.port}")
	private String port;
	
	@Value("${setup.devices.host}")
	private String devicesHost;
	
	@Autowired
	private Setup setup;
	
	public void initializeSetup() {
		setup.activate();
		
		restTemplate.getForObject(
				new StringBuilder("http://")
								.append(host)
								.append(":")
								.append(port)
								.append("/gateway/setup").toString(), String.class);
		
		for (String id : DeviceRegistry.getDevicesIds()) {
			restTemplate.getForObject(new StringBuilder("http://")
					.append(devicesHost)
					.append(":")
					.append(id)
					.append("/device/setup").toString(), String.class);
		}
	}

}

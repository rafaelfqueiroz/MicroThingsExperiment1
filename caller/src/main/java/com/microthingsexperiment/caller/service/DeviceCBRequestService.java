package com.microthingsexperiment.caller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.ActiveProfiles;
import com.microthingsexperiment.circuitbreaker.CircuitBreakerService;

@Component
@Profile("deviceCBRequest")
public class DeviceCBRequestService implements RemoteRequestService {

	@Autowired
	private CircuitBreakerService cbService;
	@Autowired
	private ActiveProfiles profiles;
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String deviceHost, String devicePort) {
		String deviceId = deviceHost+":"+devicePort;
		
		Double response = null;
		
		if (profiles.isCacheActive()) {
			response = cbService.executeGetRequest("http://"+ deviceHost + ":"+ devicePort +"/device", Double.class, deviceId);
		} else {
			response = cbService.executeGetRequest("http://"+ deviceHost + ":"+ devicePort +"/device", Double.class);
		}
		
		return response;
	}

}

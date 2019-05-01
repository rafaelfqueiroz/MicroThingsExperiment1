package com.microthingsexperiment.caller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerManager;
import com.microthingsexperiment.circuitbreaker.ResponseWrapper;

@Component
@Profile("deviceCBRequest")
public class DeviceCBRequestService implements RemoteRequestService {

	@Autowired
	private CircuitBreakerManager<Double> cbService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String deviceHost, String devicePort) {
		String deviceId = deviceHost + ":" + devicePort;

		Double result = Double.NaN;

		String baseUrl = new StringBuilder("http://").append(deviceHost).append(":").append(devicePort)
				.append("/device").toString();

		try {
			logger.info("Request Started: " + baseUrl);

			 ResponseWrapper<Double> response = cbService.executeGetRequest(baseUrl, deviceId, Double.class);
			result = response.getResponse();
			
			logger.info("Request Returned: " + baseUrl);

		} catch (Exception ex) {
			logger.info("Failure Requesting: "+baseUrl);
			throw new RuntimeException(baseUrl, ex);
		}
		return result;
	}

}

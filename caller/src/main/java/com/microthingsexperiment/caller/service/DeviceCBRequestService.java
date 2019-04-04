package com.microthingsexperiment.caller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

			result = cbService.executeGetRequest(baseUrl, Double.class, deviceId);

			logger.info("Request Returned: " + baseUrl);

		} catch (Exception ex) {
			logger.info("Failure Requesting: " + baseUrl);
			throw ex;
		}
		return result;
	}

}

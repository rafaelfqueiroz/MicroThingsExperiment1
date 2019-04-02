package com.microthingsexperiment.caller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("deviceRequest")
public class DeviceRequestService implements RemoteRequestService {

	@Autowired
	private RestTemplate restTemplate;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String host, String deviceId) {
		String baseUrl = new StringBuilder("http://")
				.append(host)
				.append(":")
				.append(deviceId)
				.append("/device").toString();
		try {
			logger.info("Request Started: "+baseUrl);
			
			Double result = restTemplate.getForObject(baseUrl, Double.class);
			
			logger.info("Request Returned: "+baseUrl);
			return result;
		} catch (Exception ex) {
			logger.info("Failure Requesting: "+baseUrl);
			logger.error("Failure Requesting: "+baseUrl,ex);
			throw ex;
		}
	}

}

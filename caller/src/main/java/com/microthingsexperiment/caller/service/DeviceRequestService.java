package com.microthingsexperiment.caller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("deviceRequest")
public class DeviceRequestService implements RemoteRequestService {

	private RestTemplate restTemplate;
	
	@Value("${request.timeout}")
	private int timeout;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public DeviceRequestService(RestTemplateBuilder rtBuilder) {
		this.restTemplate = rtBuilder.build();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String host, String deviceId) {
		
		Double result =Double.NaN;
		
		String baseUrl = new StringBuilder("http://")
				.append(host)
				.append(":")
				.append(deviceId)
				.append("/device").toString();
		try {
			logger.info("Request Started: "+baseUrl);
			
			result = restTemplate.getForObject(baseUrl, Double.class);
			
			logger.info("Request Returned: "+baseUrl);

		} catch (Exception ex) {
			logger.info("Failure Requesting: "+baseUrl);
			throw ex;
		}
		return result;
	}

}

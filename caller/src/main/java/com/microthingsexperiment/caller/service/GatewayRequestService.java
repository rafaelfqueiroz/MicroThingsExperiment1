package com.microthingsexperiment.caller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("gatewayRequest")
public class GatewayRequestService implements RemoteRequestService {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${request.host}")
	private String host;
	@Value("${request.port}")
	private String port;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String deviceId) {
		String baseUrl = new StringBuilder("http://")
				.append(host)
				.append(":")
				.append(port)
				.append("/gateway/")
				.append(deviceId).toString();
		
		try {	
			
			logger.info("Request Started: "+baseUrl);

			Double temperature = restTemplate.getForObject(baseUrl, Double.class);
			
			logger.info("Request Returned: "+baseUrl);
			
			return temperature;
		} catch (Exception ex) {
			logger.info("Failure Requesting: "+baseUrl);
			logger.error("Failure Requesting: "+baseUrl,ex);
			throw ex;
		}
	}
}

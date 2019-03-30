package com.microthingsexperiment.caller.service;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String deviceId) {
		try {
			Double temperature = restTemplate.getForObject(
					new StringBuilder("http://")
									.append(host)
									.append(":")
									.append(port)
									.append("/gateway/")
									.append(deviceId).toString(), Double.class);
			return temperature;
		} catch (Exception ex) {
			ex.getMessage();
			throw ex;
		}
	}
}

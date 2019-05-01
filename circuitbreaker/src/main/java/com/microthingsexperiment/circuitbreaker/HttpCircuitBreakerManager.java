package com.microthingsexperiment.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.ActiveProfiles;
import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;

@Component
@Profile("EnableCircuitBreaker")
public class HttpCircuitBreakerManager<T> implements CircuitBreakerManager<T> {

	private RestTemplate restTemplate;
	@Autowired
	private AbstractFallbackStrategy<T> fallback;
	@Autowired
	private ActiveProfiles profiles;
	@Autowired
	private CircuitBreakerProperties properties;

	public Logger logger = LoggerFactory.getLogger(getClass());
	
	public HttpCircuitBreakerManager(RestTemplateBuilder rtBuilder) {
		this.restTemplate = rtBuilder.build();
	}
	
	public ResponseWrapper<T> executeGetRequest(String url, String deviceId, Class<? extends T> clazz) {
		
		ResponseWrapper<T> response = new CustomDeviceHystrixCommand<>(url, deviceId, restTemplate, fallback, properties, clazz).execute();

		return response;
	}
	
}
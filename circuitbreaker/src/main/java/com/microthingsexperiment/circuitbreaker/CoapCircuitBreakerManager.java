package com.microthingsexperiment.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;

@Component
@Profile("EnableCircuitBreaker & coap")
public class CoapCircuitBreakerManager<T> implements CircuitBreakerManager<T> {
	
	@Autowired
	private CircuitBreakerProperties properties;
	@Autowired
	private AbstractFallbackStrategy<T> fallback;
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ResponseWrapper<T> executeGetRequest(String url, String deviceId, Class<? extends T> clazz) {
		ResponseWrapper<T> response = new CoapHystrixCommand<>(url, deviceId, fallback, properties, clazz).execute();
		
		return response;
	}

}

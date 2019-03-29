package com.microthingsexperiment.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class HttpCircuitBreakerService implements CircuitBreakerService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AbstractFallbackStrategy fallback;
	
	@HystrixCommand(fallbackMethod="responseFallback")
	public <T> T executeGetRequest(String url, Class<T> responseType, String cacheKey) {
		T response = restTemplate.getForObject(url, responseType);
		fallback.updateDefaultValue(cacheKey, response);
		return response;
	}
	
	@Override
	public <T> T executeGetRequest(String url, Class<T> responseType) {
		return executeGetRequest(url, responseType, null);
	}
	
	public <T> T responseFallback(String url, Class<T> responseType, String cacheKey) throws Exception {
		T cachedTemperature = fallback.getDefaultFallback(cacheKey, responseType);
		return cachedTemperature;
	}
	
	
}

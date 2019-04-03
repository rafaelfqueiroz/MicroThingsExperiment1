package com.microthingsexperiment.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
@Profile("EnableCircuitBreaker")
public class HttpCircuitBreakerService implements CircuitBreakerService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AbstractFallbackStrategy fallback;
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	
	@HystrixCommand(fallbackMethod="responseFallback", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "0"),
	        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")
	})
	public <T> T executeGetRequest(String url, Class<T> responseType, String cacheKey) {
		
		logger.info("Starting:"+"CB.executeGetRequest("+url+")");
		
		T response = restTemplate.getForObject(url, responseType);
		
		fallback.updateDefaultValue(cacheKey, response);
		
		logger.info("Returning:"+"CB.executeGetRequest("+url+"):"+response);
		
		return response;
	}
	
	@Override
	public <T> T executeGetRequest(String url, Class<T> responseType) {
		return executeGetRequest(url, responseType, null);
	}
	
	public <T> T responseFallback(String url, Class<T> responseType, String cacheKey) throws Exception {
		
		logger.info("Executing Fallback ["+fallback.getClass().getName()+"]");
		
		T cachedResult = fallback.getDefaultFallback(cacheKey, responseType);
		
		
		logger.info("Fallback cached result  ["+cachedResult+"]");


		return cachedResult;
	}
	
	
}

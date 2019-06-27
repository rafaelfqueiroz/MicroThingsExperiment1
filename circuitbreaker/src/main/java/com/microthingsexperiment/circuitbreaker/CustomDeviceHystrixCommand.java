package com.microthingsexperiment.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;

public class CustomDeviceHystrixCommand<T> extends AbstractDeviceHystrixCommand<T> {
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	private RestTemplate restTemplate;

	public CustomDeviceHystrixCommand(String url, String deviceId, RestTemplate restTemplate, 
			AbstractFallbackStrategy<T> fallback, CircuitBreakerProperties properties, Class<? extends T> clazz) {
		super(url, deviceId, fallback, properties, clazz);
		setRestTemplate(restTemplate);
	}
	
	@Override
	protected ResponseWrapper<T> run() throws Exception {
		return executeGetRequest(getUrl(), getDeviceId());
	}
	
	private ResponseWrapper<T> executeGetRequest(String url, String cacheKey) {

		logger.info("Starting:" + "CB.executeGetRequest(" + url + ")");

		T response = getRestTemplate().getForObject(url, getClazzType());

		getFallbackStrategy().updateDefaultValue(cacheKey, response);

		logger.info("Returning:" + "CB.executeGetRequest(" + url + "):" + response);

		return new ResponseWrapper<>(HttpStatus.OK.value(), response);
	}

	protected RestTemplate getRestTemplate() {
		return this.restTemplate;
	}
	private void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	


}

package com.microthingsexperiment.circuitbreaker;

public interface CircuitBreakerService {

	<T> T  executeGetRequest(String url, Class<T> responseType, String cacheKey);
	<R> R responseFallback(String url, Class<R> responseType, String cacheKey)  throws Exception;
	
}

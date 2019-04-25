package com.microthingsexperiment.circuitbreaker;

public interface CircuitBreakerManager<T> {

	ResponseWrapper<T>  executeGetRequest(String url, String cacheKey, Class<? extends T> requestType);
	
}

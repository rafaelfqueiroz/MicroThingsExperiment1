package com.microthingsexperiment.circuitbreaker;

public interface CircuitBreakerService<T> {

	T  executeGetRequest(String url, String cacheKey, Class<? extends T> requestType);
	
}

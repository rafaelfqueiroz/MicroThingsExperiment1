package com.microthingsexperiment.circuitbreaker.fallback;

public interface FallbackStrategy<T> {

	<R> R getDefaultFallback(T parameter) throws Exception;
	<R> R updateDefaultValue(T parameter, R newValue);
	
}

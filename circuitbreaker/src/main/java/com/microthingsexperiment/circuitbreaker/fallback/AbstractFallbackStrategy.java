package com.microthingsexperiment.circuitbreaker.fallback;

public abstract class AbstractFallbackStrategy<T> {

	public T getDefaultFallback(String deviceId) throws RuntimeException {
		return null;
	}
	
	public void updateDefaultValue(String deviceId, T value) {
	}
	
}

package com.microthingsexperiment.circuitbreaker.fallback;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("exceptionStrategy")
public class FallbackExceptionStrategy<T> extends AbstractFallbackStrategy<T> {

	@Override
	public T getDefaultFallback(String deviceId) throws RuntimeException {
		throw new RuntimeException("Circuit Breaker has no valid value.");
	}
	
}

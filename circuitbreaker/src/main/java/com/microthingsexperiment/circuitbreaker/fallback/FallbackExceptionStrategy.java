package com.microthingsexperiment.circuitbreaker.fallback;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("exceptionStrategy")
public class FallbackExceptionStrategy extends AbstractFallbackStrategy {

	@Override
	public <T> T getDefaultFallback(String deviceId, Class<T> clazz) throws Exception {
		throw new Exception("Circuit Breaker has no valid value.");
	}
	
}

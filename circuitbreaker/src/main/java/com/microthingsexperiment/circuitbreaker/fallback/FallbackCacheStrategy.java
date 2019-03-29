package com.microthingsexperiment.circuitbreaker.fallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.circuitbreaker.cache.CacheService;

@Component
@Profile("cacheStrategy")
public class FallbackCacheStrategy extends AbstractFallbackStrategy {
	
	@Autowired
	private CacheService service;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDefaultFallback(String deviceId, Class<T> clazz) throws Exception {
		T cachedValue = (T) service.getValue(deviceId);
		return cachedValue;
	}
}

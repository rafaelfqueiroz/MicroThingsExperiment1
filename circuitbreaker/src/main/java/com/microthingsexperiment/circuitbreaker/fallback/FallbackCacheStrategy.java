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
	
	@Override
	public void updateDefaultValue(String deviceId, Object value) {
		service.put(deviceId, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getDefaultFallback(String deviceId, Class<T> clazz) throws Exception {
		Object value = service.getValue(deviceId);
		if (value == null) {
			throw new RuntimeException("No value in cache.");
		}
		T cachedValue = (T) value;
		return cachedValue;
	}
}

package com.microthingsexperiment.circuitbreaker.cache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CacheService {

	private Map<String, Object> cache = new HashMap<>();
	
	public Object getValue(String key) {
		return cache.get(key);
	}
	
	public void put(String key, Object value) {
		cache.put(key, value);
	}
	
}

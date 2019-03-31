package com.microthingsexperiment.circuitbreaker.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("cacheStrategy")
public class CacheService {
	
	@Value("${cache.expirationTime}")
	private Long expirationTime;

	private Map<String, CacheWrapper> cache = new HashMap<>();
	
	public Object getValue(String key) {
		return cache.get(key).getValue();
	}
	
	public void put(String key, Object value) {
		cache.put(key, new CacheWrapper(expirationTime, value));
	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	public void clearCache() {
		Set<Entry<String, CacheWrapper>> entrySet = cache.entrySet();
		
		for (Entry<String, CacheWrapper> entry : entrySet) {
			entry.getValue().decreaseTime();
			
			if (entry.getValue().getTime() == 0) {
				cache.remove(entry.getKey());
			}
		}
	}
	
}

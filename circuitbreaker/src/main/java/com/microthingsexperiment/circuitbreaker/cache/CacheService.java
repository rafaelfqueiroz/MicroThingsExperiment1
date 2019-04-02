package com.microthingsexperiment.circuitbreaker.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({"EnableCircuitBreaker", "cacheStrategy"})
public class CacheService {
	
	@Value("${cache.expirationTime}")
	private Long expirationTime;

	private Map<String, CacheWrapper> cache;
	
	public CacheService() {
		Map<String, CacheWrapper> poorCache = new HashMap<>();
		cache =Collections.synchronizedMap(poorCache);
	}
	
	public Object getValue(String key) {
		CacheWrapper cacheWrapper = cache.get(key);
		if (cacheWrapper == null) {
			return null;
		}
		return cache.get(key).getValue();
	}
	
	public void put(String key, Object value) {
		cache.put(key, new CacheWrapper(expirationTime, value));
	}
	
	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	public void clearCache() {
		Set<Entry<String, CacheWrapper>> entrySet = cache.entrySet();
		Iterator<Entry<String, CacheWrapper>> cacheIterator = entrySet.iterator();
		while (cacheIterator.hasNext()) {
			Entry<String, CacheWrapper> entry = cacheIterator.next();
			CacheWrapper currentWrapper = entry.getValue();
			
			currentWrapper.decreaseTime();
			if (currentWrapper.getTime() == 0) {
				cacheIterator.remove();
			}
		}
		
	}
	
}

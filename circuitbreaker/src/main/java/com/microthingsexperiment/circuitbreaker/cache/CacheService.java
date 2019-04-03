package com.microthingsexperiment.circuitbreaker.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile({ "EnableCircuitBreaker", "cacheStrategy" })
public class CacheService {

	@Value("${cache.expirationTime}")
	private Long expirationTime;

	private Map<String, CacheWrapper> cache;

	public Logger logger = LoggerFactory.getLogger(getClass());

	public CacheService() {
		Map<String, CacheWrapper> poorCache = new HashMap<>();
		cache = Collections.synchronizedMap(poorCache);
	}

	public Object getValue(String key) {

		CacheWrapper cacheWrapper = cache.get(key);

		Object result = (cacheWrapper != null ? cacheWrapper.getValue() : null);

		return result;
	}

	public void put(String key, Object value) {
		cache.put(key, new CacheWrapper(expirationTime, value));
	}

	@Scheduled(initialDelay = 1000, fixedRate = 1000)
	public void clearCache() {

		//logger.info("Cache Cleaner Started");

		Set<Entry<String, CacheWrapper>> entrySet = cache.entrySet();
		Iterator<Entry<String, CacheWrapper>> cacheIterator = entrySet.iterator();
		
		while (cacheIterator.hasNext()) {
			Entry<String, CacheWrapper> entry = cacheIterator.next();
			CacheWrapper currentWrapper = entry.getValue();

			currentWrapper.decreaseTime();
			
			if (currentWrapper.getTime() == 0) {
				cacheIterator.remove();
				logger.info("Cached Value[key,value]=["+entry.getKey()+","+currentWrapper.getValue()+"] Removed");
			}
		}
		
		//logger.info("Cache Cleaner Finished");

	}

}

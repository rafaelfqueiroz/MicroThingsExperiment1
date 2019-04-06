package com.microthingsexperiment.circuitbreaker.cache;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Profile({"EnableCircuitBreaker", "cacheStrategy"})
public class CacheConfiguration {
	
}

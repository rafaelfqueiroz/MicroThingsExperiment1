package com.microthingsexperiment;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCircuitBreaker
@Profile("EnableCircuitBreaker")
public class CircuitBreakerConfig {

	@Value("${timeout}")
	private long timeout;
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder.setConnectTimeout(Duration.ofMillis(timeout))
				.setReadTimeout(Duration.ofMillis(timeout)).build();
	}
}

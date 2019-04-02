package com.microthingsexperiment.caller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

	@Value("${request.timeout}")
	private long timeout;
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		return builder.setConnectTimeout(Duration.ofMillis(timeout))
				.setReadTimeout(Duration.ofMillis(timeout)).build();
	}
	
}

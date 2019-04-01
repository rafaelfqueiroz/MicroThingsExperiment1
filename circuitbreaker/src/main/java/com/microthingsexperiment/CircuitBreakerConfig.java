package com.microthingsexperiment;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCircuitBreaker
@Profile("EnableCircuitBreaker")
public class CircuitBreakerConfig {

}

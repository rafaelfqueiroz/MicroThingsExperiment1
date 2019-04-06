package com.microthingsexperiment.circuitbreaker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("EnableCircuitBreaker")
@ConfigurationProperties(prefix="cb-setter")
public class CircuitBreakerProperties {

	@Value("${cb-setter.circuitBreaker.requestVolumeThreshold}")
	private int requestVolumeThreshold = 0;
	@Value("${cb-setter.metrics.rollingStats.timeInMilliseconds}")
	private int metricsRollingStatsTimeInMilliseconds = 0;
	
	public int getRequestVolumeThreshold() {
		return requestVolumeThreshold;
	}
	public void setRequestVolumeThreshold(int requestVolumeThreshold) {
		this.requestVolumeThreshold = requestVolumeThreshold;
	}
	public int getMetricsRollingStatsTimeInMilliseconds() {
		return metricsRollingStatsTimeInMilliseconds;
	}
	public void setMetricsRollingStatsTimeInMilliseconds(int metricsRollingStatsTimeInMilliseconds) {
		this.metricsRollingStatsTimeInMilliseconds = metricsRollingStatsTimeInMilliseconds;
	}
	
}

package com.microthingsexperiment.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public abstract class AbstractDeviceHystrixCommand<T> extends  HystrixCommand<ResponseWrapper<T>>  {
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	private String url;
	private String deviceId;
	private AbstractFallbackStrategy<T> fallbackStrategy;
	private Class<? extends T> clazzType;
	
	public AbstractDeviceHystrixCommand(String url, String deviceId, AbstractFallbackStrategy<T> fallback, 
			CircuitBreakerProperties properties, Class<? extends T> clazzType) {
		super(
				Setter
					.withGroupKey(HystrixCommandGroupKey.Factory.asKey(deviceId))
					.andCommandPropertiesDefaults(
							HystrixCommandProperties.Setter()
								.withCircuitBreakerRequestVolumeThreshold(properties.getRequestVolumeThreshold())
								.withMetricsRollingStatisticalWindowInMilliseconds(properties.getMetricsRollingStatsTimeInMilliseconds())
								//.withCircuitBreakerSleepWindowInMilliseconds(12000)
								.withRequestCacheEnabled(false)
							)
					.andCommandKey(HystrixCommandKey.Factory.asKey(deviceId))
				);
		setDeviceIdt(deviceId);
		setUrl(url);
		setFallbackStrategy(fallback);
		setClazzType(clazzType);
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	private void setDeviceIdt(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUrl() {
		return url;
	}

	private void setUrl(String url) {
		this.url = url;
	}
	
	protected AbstractFallbackStrategy<T> getFallbackStrategy() {
		return this.fallbackStrategy;
	}
	private void setFallbackStrategy(AbstractFallbackStrategy<T> fallback) {
		this.fallbackStrategy = fallback;
	}
	
	@Override
	protected ResponseWrapper<T> getFallback() {
		logger.info("Executing Fallback [" + getFallbackStrategy().getClass().getName() + "][" + deviceId + "]");
		
		T fallbackResult = getFallbackStrategy().getDefaultFallback(getDeviceId());
		
		logger.info("Fallback result  [" + fallbackResult + "][" + deviceId + "]");
		
		return new ResponseWrapper<>(HttpStatus.PARTIAL_CONTENT.value(), fallbackResult);
	}
	
	protected Class<? extends T> getClazzType() {
		return clazzType;
	}
	
	private void setClazzType(Class<? extends T> clazzType) {
		this.clazzType = clazzType;
	}
	
}

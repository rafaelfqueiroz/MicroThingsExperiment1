package com.microthingsexperiment.iotdeviceservice.failure;

import org.springframework.stereotype.Component;

@Component
public class TimeoutConfig {

	private Boolean timeoutFlag = false;
	
	public void enableTimeout() {
		timeoutFlag = true;
	}
	
	public void disableTimeout() {
		timeoutFlag = false;
	}
	
	public Boolean isTimeoutEnabled() {
		return timeoutFlag;
	}
	
}
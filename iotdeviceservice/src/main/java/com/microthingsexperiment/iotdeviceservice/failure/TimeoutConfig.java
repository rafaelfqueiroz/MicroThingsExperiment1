package com.microthingsexperiment.iotdeviceservice.failure;

import org.springframework.stereotype.Component;

@Component
public class TimeoutConfig {

	private Boolean timeoutFlag = false;
	private Long sleepDuration = 0l;
	
	public void enableTimeout() {
		timeoutFlag = true;
	}
	
	public void disableTimeout() {
		timeoutFlag = false;
	}
	
	public Boolean isTimeoutEnabled() {
		return timeoutFlag;
	}

	public Long getSleepDuration() {
		return sleepDuration;
	}

	public void setSleepDuration(Integer sleepDuration) {
		this.sleepDuration = sleepDuration * 1000l;
	}
	
	public void resetSleepDuration() {
		this.sleepDuration = 0l;
	}
	
}
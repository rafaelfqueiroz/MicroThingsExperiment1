package com.microthingsexperiment.iotdeviceservice;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Setup {
	
	private boolean active = false;
	private long startTime;
	
	public long getStartTime() {
		return startTime;
	}


	public void activate() {
		this.active = true;
		this.startTime = (new Date()).getTime();
	}
	

	public boolean isActive() {
		return active;
	}
	
	public long getExecutionTime() {
		long currentTime = (new Date()).getTime();
		
		return currentTime - startTime;
		
	}
}

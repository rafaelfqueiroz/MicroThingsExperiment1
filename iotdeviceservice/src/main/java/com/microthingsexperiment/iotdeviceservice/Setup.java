package com.microthingsexperiment.iotdeviceservice;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Setup {
	
	private boolean active = false;
	private long startTime;
	
	public void activate() {
		this.active = true;
		this.startTime = (new Date()).getTime();
	}
	
	public void deactivate() {
		this.active = true;
		this.startTime = 0;
	}

	public boolean isActive() {
		return active;
	}
	
	public long getExecutionTime() {
		long currentTime = (new Date()).getTime();
		
		return currentTime - startTime;
		
	}
}

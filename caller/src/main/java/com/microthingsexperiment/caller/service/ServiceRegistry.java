package com.microthingsexperiment.caller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.microthingsexperiment.caller.service.registration.Device;
import com.microthingsexperiment.caller.service.registration.Gateway;

@Configuration
@ConfigurationProperties(prefix="setup")
public class ServiceRegistry {
	
	private Gateway gateway;
	
	private List<Device> devices = new ArrayList<>();

	public List<Device> getDevices() {
		return devices;
	}
	
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Gateway getGateway() {
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

}

package com.microthingsexperiment.caller.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.microthingsexperiment.ActiveProfiles;
import com.microthingsexperiment.caller.Setup;

public abstract class SetupService {
	
	@Autowired
	private Setup setup;
	@Autowired
	private ServiceRegistry serviceRegistry;
	@Autowired
	private ActiveProfiles profiles;

	public void initializeSetup() {
		beforeSetupConfiguration();
		
		if (getProfiles().isProfileActive("gatewayRequest")) {
			setupGateway();
		}
		
		setupDevices();
		
		afterSetupConfiguration();
	}

	protected void afterSetupConfiguration() {}

	public abstract void setupDevices();

	public abstract void setupGateway();

	protected void beforeSetupConfiguration() {
		getSetup().activate();
	}

	public Setup getSetup() {
		return setup;
	}

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public ActiveProfiles getProfiles() {
		return profiles;
	}
	
}

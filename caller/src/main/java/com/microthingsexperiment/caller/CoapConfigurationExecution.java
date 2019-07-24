package com.microthingsexperiment.caller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;

public class CoapConfigurationExecution {
	
	private CoapResource[] resources;
	private int port;
	public CoapConfigurationExecution(List<CoapResource> resources, int port) {
		setResources(resources);
		this.port = port;
	}

	@PostConstruct
	public void run() {
		CoapServer server = new CoapServer(port);
		server.add(resources);

		server.start(); 
	}
	
	private void setResources(List<CoapResource> resources) {
		this.resources = new CoapResource[resources.size()];
		for (int i = 0; i < this.resources.length; i++) {
			this.resources[i] = resources.get(i);
		}
	}

}

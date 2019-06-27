package com.microthingsexperiment.caller.service;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.caller.service.registration.Device;

@Component
@Profile("coap")
public class CoapSetup extends SetupService {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void setupDevices() {
		for (Device device : getServiceRegistry().getDevices()) {
			requestForSetup(
							new StringBuilder("coap://")
							.append(device.getHost()).append(":")
							.append(device.getPort()).append("/setup").toString()
						);
		}
	}

	@Override
	public void setupGateway() {
		requestForSetup(
						new StringBuilder("coap://")
						.append(getServiceRegistry().getGateway().getHost())
						.append(":")
						.append(getServiceRegistry().getGateway().getPort()).append("/setup")
						.toString()
					);
	}
	
	private void requestForSetup(String url) {
		try {
			logger.info("Starting:" + "CoapCB.executeGetRequest(" + url + ")");
			
			CoapClient client = new CoapClient(url);
			String plainResponse = client.get(MediaTypeRegistry.APPLICATION_JSON).getResponseText();
			
			logger.info("Returning:" + "CoapCB.executeGetRequest(" + url + "):" + plainResponse);
		} catch (ConnectorException | IOException e) {
			e.printStackTrace();
		}
		
	}

}

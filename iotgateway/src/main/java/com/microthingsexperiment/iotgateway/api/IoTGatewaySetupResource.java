package com.microthingsexperiment.iotgateway.api;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.iotgateway.Setup;

@Component
@Profile("coap")
public class IoTGatewaySetupResource extends CoapResource {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Setup setup;

	public IoTGatewaySetupResource() {
		super("setup");
	}

	@Override
	public void handleGET(CoapExchange exchange) {
		setup.activate();

		logger.info("Gateway.Setup:[]");
		
		exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, "OK");
	}
	
	
}

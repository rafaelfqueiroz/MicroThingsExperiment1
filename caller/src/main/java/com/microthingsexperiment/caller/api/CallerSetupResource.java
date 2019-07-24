package com.microthingsexperiment.caller.api;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.caller.service.SetupService;

@Component
@Profile("coap")
public class CallerSetupResource extends CoapResource {
	
	@Autowired
	private SetupService setupService;
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	public CallerSetupResource() {
		super("setup");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		logger.info("Starting Caller.Setup:[]");
		setupService.initializeSetup();
		
		logger.info("Finishing Caller.Setup:[]");
		
		exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, "OK");
	}
	
}

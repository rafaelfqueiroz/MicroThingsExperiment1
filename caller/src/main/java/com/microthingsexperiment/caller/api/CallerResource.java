package com.microthingsexperiment.caller.api;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.caller.service.DeviceComputationService;

@Component
@Profile("coap")
public class CallerResource extends CoapResource {
	
	@Autowired
	private DeviceComputationService service;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	public CallerResource() {
		super("caller");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		
		try {
			logger.info("Starting:"+"Caller.call()");
			
			Double computedValue = service.compute();
			
			logger.info("Returning:"+"Caller.call():"+computedValue);
			
			exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, computedValue.toString());
		
		} catch (Exception e) {
			logger.info("Failure:"+"Caller.call()");
			logger.error("Failure to Caller.call()",e);
			
			exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
		}
		
	}

}

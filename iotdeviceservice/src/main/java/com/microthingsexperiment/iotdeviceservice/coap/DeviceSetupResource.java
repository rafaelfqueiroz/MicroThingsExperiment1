package com.microthingsexperiment.iotdeviceservice.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.iotdeviceservice.Setup;
import com.microthingsexperiment.iotdeviceservice.datareader.DataReader;
import com.microthingsexperiment.iotdeviceservice.failure.FailureManager;

@Component
@Profile("coap")
public class DeviceSetupResource extends CoapResource {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Setup setupManager;
	@Autowired
	private FailureManager failureManager;
	@Autowired
	private DataReader dataReader;

	public DeviceSetupResource() {
		super("setup");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		logger.info("Starting IoTDevice.Setup:[]");
		
		setupManager.activate();
		failureManager.isFailed();
		dataReader.setup();
		
		logger.info("Finishing IoTDevice.Setup:[]");
		
		exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, "OK");
	}

}

package com.microthingsexperiment.iotgateway.api;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerManager;
import com.microthingsexperiment.circuitbreaker.ResponseWrapper;

@Component
@Profile("coap")
public class IoTGatewayResource extends CoapResource {
	
	@Autowired
	private CircuitBreakerManager<Double> cbService;

	public Logger logger = LoggerFactory.getLogger(getClass());

	public IoTGatewayResource() {
		super("gateway");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		String deviceHost = exchange.getQueryParameter("host");
		int devicePort = Integer.parseInt(exchange.getQueryParameter("deviceId"));
		String deviceId =  deviceHost + ":" + devicePort;
		
		logger.info("Starting:" + "Gateway.getDeviceValue(" + deviceId + ")");
		
		try {
		
			Double response = null;
		
			ResponseWrapper<Double> wrapper = cbService.executeGetRequest("coap://" + deviceHost + ":" + devicePort + "/device",
					deviceId, Double.class);
		
			response = wrapper.getResponse();
		
			logger.info("Returning:" + "Gateway.getDeviceValue(" + deviceId + "):" + response);
			
			exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, response.toString());
		} catch (Exception e) {
			logger.info("Failure:" + "Gateway.getDeviceValue(" + deviceId + ")");
			logger.info("Failure message Gateway.getDeviceValue" + e.getMessage());
			
			logger.error("Failure to Gateway.getDeviceValue(" + deviceId + ")", e);
			
			exchange.respond(ResponseCode.GATEWAY_TIMEOUT);
		}
		
		
	}

}

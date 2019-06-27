package com.microthingsexperiment.iotdeviceservice.coap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.iotdeviceservice.Setup;
import com.microthingsexperiment.iotdeviceservice.datareader.DataReader;
import com.microthingsexperiment.iotdeviceservice.failure.FailureManager;

@Component
@Profile("coap")
public class IoTDeviceResource extends CoapResource {
	
	@Autowired
	private FailureManager failureManager;
	@Autowired
	private Setup setupManager;
	@Autowired
	private DataReader dataReader;
	@Value("${omission.duration:5000}")
	private int omissionLockDuration;
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	public IoTDeviceResource() {
		super("device");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		logger.info("Starting:"+"getDeviceData()");
		
		try {
			if (failureManager.isFailed()) {
				synchronized(this) {
				  logger.info("Omission:IoTDevice.getDeviceData()[ExecutionTime(ms)="+ setupManager.getExecutionTime() + "]");
				   wait(omissionLockDuration);
				}
				exchange.respond(ResponseCode.SERVICE_UNAVAILABLE);
			}
			else {
				Double reading = dataReader.getNextValue();
				
				logger.info("Returning:"+"IoTDevice.getDeviceData():"+reading);
				
				exchange.respond(ResponseCode._UNKNOWN_SUCCESS_CODE, reading.toString());
			}
			
			
		} catch (Exception e) {
			logger.info("Failure:"+"IoTDevice.getDeviceData()");
			logger.error("Failure to IoTDevice.getDeviceData",e);
			
			exchange.respond(ResponseCode.INTERNAL_SERVER_ERROR);
		}
		
	}

}

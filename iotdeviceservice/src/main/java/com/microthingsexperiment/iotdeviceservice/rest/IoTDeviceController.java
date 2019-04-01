package com.microthingsexperiment.iotdeviceservice.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microthingsexperiment.iotdeviceservice.Setup;
import com.microthingsexperiment.iotdeviceservice.datareader.DataReader;
import com.microthingsexperiment.iotdeviceservice.failure.FailureManager;

@Controller
@RequestMapping("/device")
public class IoTDeviceController {
	
	@Autowired
	private FailureManager failureManager;
	@Autowired
	private Setup setupManager;
	@Autowired
	private DataReader dataReader;
	
	@Value("${omission.duration:5000}")
	private int omissionLockDuration;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> getDeviceData() {
		
		logger.info("Starting:"+"getDeviceData()");
		
		ResponseEntity<Double> response;
		
		
		try {
			if (failureManager.isFailed()) {
				synchronized(this) {
				  logger.info("Omission:IoTDevice.getDeviceData()[ExecutionTime(ms)="+ setupManager.getExecutionTime() + "]");
				   wait(omissionLockDuration);
				}
				response = new ResponseEntity<Double>(HttpStatus.SERVICE_UNAVAILABLE);
			}
			else {
				Double reading = dataReader.getNextValue();
				
				logger.info("Returning:"+"IoTDevice.getDeviceData():"+reading);
				
				response = new ResponseEntity<Double>(reading, HttpStatus.OK);	
			}
			
			
		} catch (Exception e) {
			logger.info("Failure:"+"IoTDevice.getDeviceData()");
			logger.error("Failure to IoTDevice.getDeviceData",e);
			
			response = new ResponseEntity<Double>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		
		setupManager.activate();
		
		logger.info("IoTDevice.Setup:[]");
		
		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}


}

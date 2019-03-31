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
	private Setup setup;
	@Autowired
	private DataReader dataReader;
	
	@Value("${omission.duration:5000}")
	private int omissionLockDuration;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> getDeviceData() throws InterruptedException {
		logger.debug(new StringBuilder("STARTING ").append(new Throwable() 
                .getStackTrace()[0] 
                .getMethodName()).toString());
		try {
			if (failureManager.isFailed()) {
				synchronized(this) {
				  logger.debug("OMISSION FAILURE ["+ setup.getExecutionTime() + " ms]");
				   wait(omissionLockDuration);
				}
			}
			
			Double response = dataReader.getNextValue();
			
			logger.debug(new StringBuilder("RETURNING: ").append(response).toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("FAILURE ", e);
			logger.error("Failure to getDeviceData",e);
			throw e;
		}
	}
	
	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		setup.activate();
		logger.debug("SETUP ");
		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}
	
	@GetMapping("/reset")
	public ResponseEntity<String> reset() {
		setup.deactivate();
		logger.debug("RESET ");
		return new ResponseEntity<>("RESET OK", HttpStatus.OK);
	}

}

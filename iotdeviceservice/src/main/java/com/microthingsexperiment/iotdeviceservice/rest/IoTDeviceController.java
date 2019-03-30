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
import com.microthingsexperiment.iotdeviceservice.failure.TimeoutConfig;

@Controller
@RequestMapping("/device")
public class IoTDeviceController {
	
	@Autowired
	private TimeoutConfig timeoutConfig;
	@Autowired
	private Setup setup;
	@Autowired
	private DataReader dataReader;
	
	@Value("${timeout.sleepTime}")
	private Long sleepTime;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> getDeviceData() throws InterruptedException {
		logger.debug(new StringBuilder("STARTING ").append(new Throwable() 
                .getStackTrace()[0] 
                .getMethodName()).toString());
		try {
			if (timeoutConfig.isTimeoutEnabled()) {
				Thread.sleep(timeoutConfig.getSleepDuration());
			}
			
			Double response = dataReader.getNextValue();
			
			logger.debug(new StringBuilder("RETURNING: ").append(response).toString());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			logger.debug("FAILURE ", e);
			throw e;
		}
	}
	
	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		setup.activate();
		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}

}

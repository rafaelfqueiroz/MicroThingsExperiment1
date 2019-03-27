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

import com.microthingsexperiment.iotdeviceservice.datareader.DataReader;
import com.microthingsexperiment.iotdeviceservice.failure.TimeoutConfig;

@Controller
@RequestMapping("/device")
public class IoTDeviceController {
	
	@Autowired
	private TimeoutConfig timeoutConfig;
	@Autowired
	private DataReader dataReader;
	
	@Value("${timeout.sleepTime}")
	private Long sleepTime;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> getDeviceData() throws InterruptedException {
		logger.info("Receiving request for device data.");
		try {
			if (timeoutConfig.isTimeoutEnabled()) {
				Thread.sleep(sleepTime);
			} 
			
			Double response = dataReader.getNextValue();
			
			logger.info("Returning the device data: ", response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (InterruptedException e) {
			e.printStackTrace();
			
			logger.info("FAILURE OCURRENCE", e);
			throw e;
		}
	}

}

package com.microthingsexperiment.caller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microthingsexperiment.caller.service.DeviceComputationService;
import com.microthingsexperiment.caller.service.HttpSetup;

@Controller
@RequestMapping("/caller")
public class CallerController {
	
	@Autowired
	private DeviceComputationService service;
	
	@Autowired
	private HttpSetup setupService;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> call() {
		
		ResponseEntity<Double> response;
		
		try {
			logger.info("Starting:"+"Caller.call()");

			
			Double computedValue = service.compute();
			
			logger.info("Returning:"+"Caller.call():"+computedValue);

			response = new ResponseEntity<>(computedValue, HttpStatus.OK);
			
		} catch (Exception e) {
			
			logger.info("Failure:"+"Caller.call()");
			logger.error("Failure to Caller.call()",e);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("error-message", e.getCause().toString());
			
			response = new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
		
	}
	
	
	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		
		logger.info("Starting Caller.Setup:[]");
		setupService.initializeSetup();
		
		logger.info("Finishing Caller.Setup:[]");
		
		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}

}

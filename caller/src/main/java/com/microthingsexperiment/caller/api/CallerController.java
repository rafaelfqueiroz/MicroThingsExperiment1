package com.microthingsexperiment.caller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microthingsexperiment.caller.service.DeviceComputationService;

@Controller
@RequestMapping("/caller")
public class CallerController {
	
	@Autowired
	private DeviceComputationService service;  
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<Double> call() {
		try {
			logger.debug(new StringBuilder("STARTING ").append(getClass().getEnclosingMethod().getName()).toString());
			
			Double computedValue = service.compute();
			
			logger.debug(new StringBuilder("RETURNING: ").append(computedValue).toString());
			return new ResponseEntity<>(computedValue, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			logger.debug("FAILURE ", e);
			throw e;
		}
		
	}

}

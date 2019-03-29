package com.microthingsexperiment.iotgateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerService;

@RestController
@RequestMapping("/gateway")
public class IoTGatewayController {
	
	@Autowired
	private CircuitBreakerService cbService;
	@Value("${host}")
	private String host;
	
	public Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/{deviceId}")
	public Double getDeviceValue(@PathVariable("deviceId") String deviceId) {
		logger.debug(new StringBuilder("STARTING ").append(getClass().getEnclosingMethod().getName()).toString());
		try {
			
			Double response = cbService.executeGetRequest("http://"+ host + ":"+ deviceId +"/device", Double.class);
			
			logger.debug(new StringBuilder("RETURNING: ").append(response).toString());
			return response;
		} catch (Exception e){
			logger.debug("FAILURE ", e);
			throw e;
		}
		
	}
	
}

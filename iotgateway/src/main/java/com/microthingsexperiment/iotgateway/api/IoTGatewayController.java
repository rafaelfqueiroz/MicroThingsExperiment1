package com.microthingsexperiment.iotgateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerService;
import com.microthingsexperiment.iotgateway.Setup;


@RestController
@RequestMapping("/gateway")
public class IoTGatewayController {
	
	@Autowired
	private CircuitBreakerService cbService;
	@Autowired
	private Setup setup;
	
	@Value("${host}")
	private String host;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/{deviceId}")
	public Double getDeviceValue(@PathVariable("deviceId") String deviceId) {
		logger.debug(new StringBuilder("STARTING ").append(new Throwable() 
                .getStackTrace()[0] 
                .getMethodName()).toString());
		try {
			
			Double response = cbService.executeGetRequest("http://"+ host + ":"+ deviceId +"/device", Double.class);
			
			logger.debug(new StringBuilder("RETURNING: ").append(response).toString());
			return response;
		} catch (Exception e){
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

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

import com.microthingsexperiment.ActiveProfiles;
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
	@Autowired
	private ActiveProfiles profiles;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/{deviceId}")
	public Double getDeviceValue(@PathVariable("deviceId") String deviceId) {
		logger.info("Starting:"+"Gateway.getDeviceValue("+deviceId+")");

		try {
			Double response = null;
			if (profiles.isCacheActive()) {
				response = cbService.executeGetRequest("http://"+ host + ":"+ deviceId +"/device", Double.class, deviceId);
			} else {
				response = cbService.executeGetRequest("http://"+ host + ":"+ deviceId +"/device", Double.class);
			}
			
			logger.info("Returning:"+"Gateway.getDeviceValue("+deviceId+"):"+response);
			
			return response;
		} catch (Exception e){
			logger.info("Failure:"+"Gateway.getDeviceValue("+deviceId+")");
			logger.error("Failure to Gateway.getDeviceValue("+deviceId+")",e);
			throw e;
		}
		
	}
	
	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		setup.activate();
		
		
		logger.info("Gateway.Setup:[]");
		
		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}

}

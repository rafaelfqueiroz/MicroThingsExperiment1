package com.microthingsexperiment.iotgateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerService;
import com.microthingsexperiment.iotgateway.Setup;

@RestController
@RequestMapping("/gateway")
public class IoTGatewayController {

	@Autowired
	private CircuitBreakerService<Double> cbService;
	@Autowired
	private Setup setup;

	public Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping
	public Double getDeviceValue(@RequestHeader("device-host") String deviceHost,
			@RequestHeader("device-port") String devicePort) {
		String deviceId = deviceHost + ":" + devicePort;

		logger.info("Starting:" + "Gateway.getDeviceValue(" + deviceId + ")");

		try {
			Double response = null;

			response = cbService.executeGetRequest("http://" + deviceHost + ":" + devicePort + "/device",
					deviceId, Double.class);

			logger.info("Returning:" + "Gateway.getDeviceValue(" + deviceId + "):" + response);

			return response;
		} catch (Exception e) {
			logger.info("Failure:" + "Gateway.getDeviceValue(" + deviceId + ")");
			logger.error("Failure to Gateway.getDeviceValue(" + deviceId + ")", e);
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

package com.microthingsexperiment.iotgateway.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microthingsexperiment.circuitbreaker.CircuitBreakerManager;
import com.microthingsexperiment.circuitbreaker.ResponseWrapper;
import com.microthingsexperiment.iotgateway.Setup;

@RestController
@RequestMapping("/gateway")
@Profile("http")
public class IoTGatewayController {

	@Autowired
	private CircuitBreakerManager<Double> cbService;
	@Autowired
	private Setup setup;

	public Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping
	public ResponseEntity<Double> getDeviceValue(@RequestHeader("device-host") String deviceHost,
			@RequestHeader("device-port") String devicePort) {
		String deviceId = deviceHost + ":" + devicePort;

		logger.info("Starting:" + "Gateway.getDeviceValue(" + deviceId + ")");

		try {
			Double response = null;

			ResponseWrapper<Double> wrapper = cbService.executeGetRequest("http://" + deviceHost + ":" + devicePort + "/device",
					deviceId, Double.class);

			response = wrapper.getResponse();
			logger.info("Returning:" + "Gateway.getDeviceValue(" + deviceId + "):" + response);

			return new ResponseEntity<>(response, HttpStatus.resolve(wrapper.getStatusCode()));
		} catch (Exception e) {
			logger.info("Failure:" + "Gateway.getDeviceValue(" + deviceId + ")");
			logger.info("Failure message Gateway.getDeviceValue" + e.getMessage());
			
			logger.error("Failure to Gateway.getDeviceValue(" + deviceId + ")", e);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("error-message", e.getCause().toString());
			
			return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).headers(headers).body(Double.NaN);
		}

	}

	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		setup.activate();

		logger.info("Gateway.Setup:[]");

		return new ResponseEntity<>("SETUP OK", HttpStatus.OK);
	}

}

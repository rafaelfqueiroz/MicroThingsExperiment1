package com.microthingsexperiment.circuitbreaker;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microthingsexperiment.circuitbreaker.fallback.AbstractFallbackStrategy;

public class CoapHystrixCommand<T> extends AbstractDeviceHystrixCommand<T>{
	public Logger logger = LoggerFactory.getLogger(getClass());

	public CoapHystrixCommand(String url, String deviceId, AbstractFallbackStrategy<T> fallback, CircuitBreakerProperties properties, Class<? extends T> clazzType) {
		super(url, deviceId, fallback, properties, clazzType);
	}

	@Override
	protected ResponseWrapper<T> run() throws Exception {
		return executeGetRequest(getUrl(), getDeviceId());
	}
	
	private ResponseWrapper<T> executeGetRequest(String url, String cacheKey) {

		logger.info("Starting:" + "CoapCB.executeGetRequest(" + url + ")");
		
		CoapClient client = new CoapClient(url);
		T response = null;
		
		try {
			String plainResponse = client.get(MediaTypeRegistry.APPLICATION_JSON).getResponseText();
			ObjectMapper om = new ObjectMapper();
			response = om.readValue(plainResponse, getClazzType());
			
			getFallbackStrategy().updateDefaultValue(cacheKey, response);

			logger.info("Returning:" + "CoapCB.executeGetRequest(" + url + "):" + response);

		} catch (ConnectorException | IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseWrapper<>(ResponseCode._UNKNOWN_SUCCESS_CODE.value, response);
		
	}

}

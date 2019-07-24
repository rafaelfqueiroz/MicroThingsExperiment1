package com.microthingsexperiment.caller.service.request;

import java.io.IOException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("deviceRequest & coap")
public class DeviceCoapRequestService implements RemoteRequestService {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${request.timeout}")
	private long timeout;
	
	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String host, String deviceId) {
		
		Double result =Double.NaN;
		
		String baseUrl = new StringBuilder("coap://")
				.append(host)
				.append(":")
				.append(deviceId)
				.append("/device").toString();
		try {
			logger.info("Request Started: "+baseUrl);
			
			CoapClient client = new CoapClient(baseUrl);
			client.setTimeout(timeout);
			
			String plainResponse = client.get(MediaTypeRegistry.APPLICATION_JSON).getResponseText();
			ObjectMapper om = new ObjectMapper();
			result = om.readValue(plainResponse, Double.class);
			
			logger.info("Request Returned: "+baseUrl);

		} catch (RuntimeException ex) {
			logger.info("Failure Requesting: "+baseUrl);
			ex.printStackTrace();
			throw ex;
		} catch (ConnectorException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}
}

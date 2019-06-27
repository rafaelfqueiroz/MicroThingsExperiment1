package com.microthingsexperiment.caller.service.request;

import java.io.IOException;


import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Profile("gatewayRequest & coap")
public class GatewayCoapRequestService implements RemoteRequestService {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${setup.gateway.host}")
	private String gatewayHost;
	@Value("${setup.gateway.port}")
	private String gatewayPort;
	@Value("${request.timeout}")
	private long timeout;

	@SuppressWarnings("unchecked")
	@Override
	public Double requestData(String host, String deviceId) {
		
		String baseUrl = new StringBuilder("coap://")
				.append(gatewayHost)
				.append(":")
				.append(gatewayPort)
				.append("/gateway").toString();
		
		CoapClient client = new CoapClient(baseUrl);
		client.setTimeout(timeout);
		Double result = null;
		
		try {
			logger.info("Request Started: "+baseUrl+"->["+host+":"+deviceId+"]");
			
			Request request = Request.newGet();
			request.getOptions()
					.setAccept(MediaTypeRegistry.APPLICATION_JSON)
					.setUriQuery(new StringBuilder("host=").append(host).append("&").append("deviceId=").append(deviceId).toString())
					.addUriQuery(host).addUriQuery(deviceId);

			String plainResponse = client.advanced(request).getResponseText();
			ObjectMapper om = new ObjectMapper();
			result = om.readValue(plainResponse, Double.class);

			logger.info("Request Returned: "+baseUrl+"->["+host+":"+deviceId+"]");

		} catch (ConnectorException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}

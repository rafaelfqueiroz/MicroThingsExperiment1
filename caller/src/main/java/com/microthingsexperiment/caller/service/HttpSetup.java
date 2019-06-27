package com.microthingsexperiment.caller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.microthingsexperiment.ActiveProfiles;
import com.microthingsexperiment.caller.Setup;
import com.microthingsexperiment.caller.service.registration.Device;

@Component
@Profile("http")
public class HttpSetup extends SetupService {

	private RestTemplate restTemplate;

	@Value("${timeout:1000}")
	private int timeout;
	
	public HttpSetup(RestTemplateBuilder rtBuilder) {
		this.restTemplate = rtBuilder.build();
	}

	@Override
	protected void beforeSetupConfiguration() {
		super.beforeSetupConfiguration();
		configureRestTemplateForSetup();
	}

	private void configureRestTemplateForSetup() {
		SimpleClientHttpRequestFactory  rf = (SimpleClientHttpRequestFactory ) restTemplate
				.getRequestFactory();

		rf.setReadTimeout(10000);
		rf.setConnectTimeout(10000);
	}
	
	@Override
	protected void afterSetupConfiguration() {
		configureRestTemplateForCalls();
	}
	
	private void configureRestTemplateForCalls() {
		SimpleClientHttpRequestFactory  rf = (SimpleClientHttpRequestFactory ) restTemplate
				.getRequestFactory();

		rf.setReadTimeout(timeout);
		rf.setConnectTimeout(timeout);
		
	}

	@Override
	public void setupDevices() {
		restTemplate.getForObject(
				new StringBuilder("http://").append(getServiceRegistry().getGateway().getHost()).append(":")
						.append(getServiceRegistry().getGateway().getPort()).append("/gateway/setup").toString(),
				String.class);
	}

	@Override
	public void setupGateway() {
		for (Device device : getServiceRegistry().getDevices()) {
			restTemplate.getForObject(new StringBuilder("http://").append(device.getHost()).append(":")
					.append(device.getPort()).append("/device/setup").toString(), String.class);
		}
	}

}

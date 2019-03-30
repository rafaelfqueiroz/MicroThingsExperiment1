package com.microthingsexperiment.caller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("average")
public class DeviceComputationService implements DeviceComputation {

	@Autowired
	private RemoteRequestService remoteService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Double compute() {
		
		List<Double> temperatures = new ArrayList<>();
		for (String port : DeviceRegistry.getDevicesIds()) {
			temperatures.add(
						remoteService.requestData(port)
					);
		}
		return temperatures.stream().mapToDouble(a -> a).average().getAsDouble();
	}

}

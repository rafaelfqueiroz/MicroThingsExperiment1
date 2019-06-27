package com.microthingsexperiment.caller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.microthingsexperiment.caller.service.registration.Device;
import com.microthingsexperiment.caller.service.request.RemoteRequestService;

@Service
@Profile("average")
public class DeviceComputationService implements DeviceComputation {

	@Autowired
	private RemoteRequestService remoteService;
	
	@Autowired
	private ServiceRegistry serviceRegistry;
	
	@SuppressWarnings("unchecked")
	@Override
	public Double compute() {
		
		List<Double> presences = new ArrayList<>();
		
		for (Device device : serviceRegistry.getDevices()) {
			presences.add(
						remoteService.requestData(device.getHost(), device.getPort())
					);
		}
		return presences.stream().mapToDouble(a -> a).average().getAsDouble();
	}

}

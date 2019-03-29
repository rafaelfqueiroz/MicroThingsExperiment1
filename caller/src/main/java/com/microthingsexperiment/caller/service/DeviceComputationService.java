package com.microthingsexperiment.caller.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("average")
public class DeviceComputationService implements DeviceComputation {

	@SuppressWarnings("unchecked")
	@Override
	public Double compute() {
		return null;
	}

}

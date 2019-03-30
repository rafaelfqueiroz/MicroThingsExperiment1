package com.microthingsexperiment.caller.service;

import java.util.ArrayList;
import java.util.List;

public class DeviceRegistry {

	public static List<String> getDevicesIds() {
		List<String> ports = new ArrayList<String>();
		ports.add("8091");
		ports.add("8092");
		ports.add("8093");
		return ports;
	}
	
}

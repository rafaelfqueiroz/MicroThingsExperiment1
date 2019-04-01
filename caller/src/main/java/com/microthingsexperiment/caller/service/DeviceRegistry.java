package com.microthingsexperiment.caller.service;

import java.util.ArrayList;
import java.util.List;

public class DeviceRegistry {

	public static List<String> getDevicesIds() {
		List<String> ports = new ArrayList<String>();
		ports.add("8081");
		ports.add("8082");
		ports.add("8083");
		return ports;
	}
	
}

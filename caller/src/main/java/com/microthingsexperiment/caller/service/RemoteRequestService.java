package com.microthingsexperiment.caller.service;

public interface RemoteRequestService {

	<T> T requestData(String host,String deviceId);
	
	default void requestSetup() {}
	
}

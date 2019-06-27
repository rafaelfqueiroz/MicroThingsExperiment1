package com.microthingsexperiment.caller.service.request;

public interface RemoteRequestService {

	<T> T requestData(String host, String deviceId);
	
	default void requestSetup() {}
	
}

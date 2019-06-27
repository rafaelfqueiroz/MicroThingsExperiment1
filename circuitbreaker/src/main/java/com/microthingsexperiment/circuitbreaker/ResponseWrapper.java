package com.microthingsexperiment.circuitbreaker;

public class ResponseWrapper<T> {
	
	private int statusCode;
	private T response;
	
	public ResponseWrapper(int statusCode, T response) {
		setStatusCode(statusCode);
		setResponse(response);
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}
	
}

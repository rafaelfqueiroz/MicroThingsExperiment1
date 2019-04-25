package com.microthingsexperiment.circuitbreaker;

import org.springframework.http.HttpStatus;

public class ResponseWrapper<T> {
	
	private HttpStatus status;
	private T response;
	
	public ResponseWrapper(HttpStatus status, T response) {
		setStatus(status);
		setResponse(response);
	}
	
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}
	
}

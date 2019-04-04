package com.microthingsexperiment.circuitbreaker.cache.exception;

public class FallbackException extends RuntimeException {

	private static final long serialVersionUID = 8782065235807056580L;
	public FallbackException(String message) {
		super(message);
	}
	

}

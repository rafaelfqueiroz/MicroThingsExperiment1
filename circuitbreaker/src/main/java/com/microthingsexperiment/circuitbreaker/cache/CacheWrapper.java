package com.microthingsexperiment.circuitbreaker.cache;

public class CacheWrapper {

	private Long time;
	private Object value;
	
	private static final long MILLI = 1000;
	
	public CacheWrapper(Long expirationTime, Object value) {
		setTime(expirationTime);
		setValue(value);
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Object getValue() {
		return value;
	}
	private void setValue(Object value) {
		this.value = value;
	}
	public void decreaseTime() {
		setTime(getTime() -  MILLI);
	}
	
}

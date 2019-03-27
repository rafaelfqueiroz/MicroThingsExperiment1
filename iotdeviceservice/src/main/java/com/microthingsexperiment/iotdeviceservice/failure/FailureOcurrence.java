package com.microthingsexperiment.iotdeviceservice.failure;

public class FailureOcurrence {

	private Integer moment;
	private Integer duration;
	
	public FailureOcurrence(Integer moment, Integer duration) {
		this.moment = moment;
		this.duration = duration;
	}
	
	public Integer getMoment() {
		return moment;
	}
	public void setMoment(Integer moment) {
		this.moment = moment;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
}

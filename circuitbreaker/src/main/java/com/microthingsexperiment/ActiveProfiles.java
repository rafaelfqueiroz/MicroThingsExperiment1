package com.microthingsexperiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ActiveProfiles {

	@Autowired
	private Environment env;
	
	public boolean isCacheActive() {
		return isProfileActive("cacheStrategy");
	}

	public boolean isProfileActive(String profile) {
		String[] activeProfiles = env.getActiveProfiles();
		for (String profileName : activeProfiles) {
			if (profileName.equals(profile)) {
				return true;
			}
		}
		return false;
	}
	
}

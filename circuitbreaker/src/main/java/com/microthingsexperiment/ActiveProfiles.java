package com.microthingsexperiment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ActiveProfiles {

	@Autowired
	private Environment env;
	
	public boolean isCacheActive() {
		String[] activeProfiles = env.getActiveProfiles();
		
		for (String profileName : activeProfiles) {
			if (profileName.equals("cacheStrategy")) {
				return true;
			}
		}
		return false;
	}
	
}

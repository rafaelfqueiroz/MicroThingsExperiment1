package com.microthingsexperiment.iotdeviceservice.failure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class FailureOcurrenceService implements ApplicationRunner {
	
	@Autowired
	private TimeoutConfig timoutConfig;

	@Value("${failure.file.path}")
	private String failureFilePath;
	
	private Map<Integer, FailureOcurrence> failures = new HashMap<>();
	
	
	@PostConstruct
	private void loadFailureFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(failureFilePath));
			for (String line : lines) {
				String[] lineValues = line.split(";");
				for (String value : lineValues) {
					String[] split = value.split(":");
					Integer failureMoment = Integer.valueOf(split[0]);
					Integer failureDuration = Integer.valueOf(split[1]);
					failures.put(failureMoment, new FailureOcurrence(failureMoment, failureDuration));
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final int MILLI = 1000;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Integer tick = 0;
		
		Integer failureTick = 0;
		Integer failureDuration = 0;
		
		FailureOcurrence currentFailure = null;
		
		while (true) {
			Thread.sleep(MILLI);
			tick++;
			
			if (currentFailure == null) {
				currentFailure = failures.get(tick);
				
				if (currentFailure != null) {
					failureDuration = currentFailure.getDuration();
					timoutConfig.enableTimeout();
				} else {
					continue;
				}
			}
			
			if (failureTick < failureDuration) {
				failureTick++;
			} else {
				failureTick = 0;
				currentFailure = null;
				timoutConfig.disableTimeout();
			}
		}
	}
	
}

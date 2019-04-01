package com.microthingsexperiment.iotdeviceservice.failure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microthingsexperiment.iotdeviceservice.Setup;

@Component
public class FailureManager {

	@Value("${failure.file.path}")
	private String failureFilePath;

	@Autowired
	private Setup setup;

	private List<FailureOcurrence> failures = new ArrayList<FailureOcurrence>();

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
					failures.add(new FailureOcurrence(failureMoment*1000l, failureDuration*1000l));
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFailed() {
		long executionTime = setup.getExecutionTime();
		boolean isFailed = false;

		if (setup.isActive()) {

			for (FailureOcurrence failure : failures) {

				if (executionTime >= failure.getStart() && executionTime <= failure.getEnd()) {
					isFailed = true;
					break;
				}
			}

		}
		
		return isFailed;

	}

}

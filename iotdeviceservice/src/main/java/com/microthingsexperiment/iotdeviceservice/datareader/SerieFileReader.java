package com.microthingsexperiment.iotdeviceservice.datareader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SerieFileReader implements DataReader {

	@Value("${serie.file.path}")
	private String serieFilePath;
	
	private List<Double> serie = new ArrayList<>();
	
	private int index;
	
	@PostConstruct
	private void loadSerieFile() {
		try {
			List<String> lines = Files.readAllLines(Paths.get(serieFilePath));
			for (String line : lines) {
				String[] lineValues = line.split(";");
				for (String value : lineValues) {
					serie.add(Double.valueOf(value));
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Double getNextValue() {
		Double currentValue = serie.get(index);
		updateIndex();
		return currentValue;
	}

	private void updateIndex() {
		index++;
		if (index == serie.size()) {
			index = 0;
		}
	}

}

package com.microthingsexperiment.iotdeviceservice.datareader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SerieFileReader implements DataReader {

	@Value("${serie.file.path}")
	private String serieFilePath;

	private List<Double> serie = new ArrayList<>();

	public Logger logger = LoggerFactory.getLogger(getClass());

	private int index;

	@PostConstruct
	private void loadSerieFile() {
		try {
			logger.info("Starting the load of the data series");

			List<String> lines = Files.readAllLines(Paths.get(serieFilePath));
			
			for (String line : lines) {
				String[] lineValues = line.split(";");
				for (String value : lineValues) {
					serie.add(Double.valueOf(value));
				}

				logger.info("Data series loaded");

			}
		} catch (IOException e) {
			logger.info("Error trying to load data series file (" + serieFilePath
					+ "). Considering only one default value");
			serie.add(0d);
		}
	}

	public void setup() {
		// Método dummy apenas para ser chamado e garantir que houve a inicialização
		// adequada do bean

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

package com.ptc.computationNew;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComputationService {

	private final ComputationRuleFactory ruleFactory;

	public ComputationService(ComputationRuleFactory ruleFactory) {
		this.ruleFactory = ruleFactory;
	}

	public List<String[]> csvLines(String fileName) throws IOException, CsvException {
		CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

		Path csvFile = Paths.get(fileName);

		try (BufferedReader br = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8);
			 CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {

			return reader.readAll();
		}
	}

	public List<ComputationRule> compute(String fileName) throws IOException, CsvException {
		List<String[]> lines = csvLines(fileName);
		List<ComputationRule> rules = new ArrayList<>();
		for (String[] line : lines) {
			rules.add(ruleFactory.computeRule(line, lines, rules));
		}
		return rules;
	}

}


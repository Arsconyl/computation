package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.ComputationRule;
import com.ptc.computation.rules.ComputationRuleFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ptc.computation.ComputeConst.*;

public class ComputationThreadedService implements Runnable {

	private final ComputationRuleFactory ruleFactory;

	private final ComputationCSVService csvService;

	private final UUID uuid;

	private File fileToProduce;

	public ComputationThreadedService(ComputationRuleFactory ruleFactory, ComputationCSVService csvService, UUID uuid) {
		this.ruleFactory = ruleFactory;
		this.csvService = csvService;
		this.uuid = uuid;
	}

	@Override
	public void run() {
		List<String[]> lines;
		List<ComputationRule> rules = new ArrayList<>();
		try {
			lines = csvService.csvLines(FILEPATH + FILENAME + uuid + CSV_EXTENSION + ".in");
			for (String[] line : lines) {
				rules.add(ruleFactory.computeRule(line, lines, rules));
			}
			rules = ruleFactory.computeTotal(rules);
			fileToProduce = csvService.csvFile(FILEPATH + FILENAME + uuid + CSV_EXTENSION, rules);
		} catch (IOException | CsvException e) {
			throw new ComputeException("Error while reading csv file");
		}
	}

	public File getFileToProduce() {
		return fileToProduce;
	}
}

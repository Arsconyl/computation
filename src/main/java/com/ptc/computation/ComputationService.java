package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.*;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ComputationService {

	private final ComputationRuleFactory ruleFactory;

	private final ComputationCSVService csvService;

	public ComputationService(ComputationRuleFactory ruleFactory, ComputationCSVService csvService) {
		this.ruleFactory = ruleFactory;
		this.csvService = csvService;
	}

	public List<ComputationRule> compute(String fileName) throws IOException, CsvException, ComputeException {
		List<String[]> lines = csvService.csvLines(fileName);
		List<ComputationRule> rules = new ArrayList<>();
		for (String[] line : lines) {
			rules.add(ruleFactory.computeRule(line, lines, rules));
		}
		return rules;
	}

	public Set<Description> getAllRules() {
		Reflections simpleRules = new Reflections("com.ptc.computation.rules.impl.simple");
		Reflections customRules = new Reflections("com.ptc.computation.rules.impl.custom");

		Set<Class<? extends SimpleComputationRule>> simpleRulesList = simpleRules.getSubTypesOf(SimpleComputationRule.class);
		Set<Class<? extends CustomComputationRule>> customRulesList = customRules.getSubTypesOf(CustomComputationRule.class);

		List<Description> simpleRulesDescriptions = simpleRulesList.stream().map(rule -> rule.getAnnotation(Description.class)).toList();
		List<Description> customRulesDescriptions = customRulesList.stream().map(rule -> rule.getAnnotation(Description.class)).toList();

		return Stream.concat(simpleRulesDescriptions.stream(), customRulesDescriptions.stream()).collect(Collectors.toSet());
	}

}


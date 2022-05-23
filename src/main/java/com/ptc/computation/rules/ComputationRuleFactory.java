package com.ptc.computation.rules;

import com.ptc.computation.ComputeException;
import com.ptc.computation.rules.impl.TotalRule;
import com.ptc.computation.rules.impl.custom.*;
import com.ptc.computation.rules.impl.simple.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComputationRuleFactory {
	public ComputationRule computeRule(String[] row, List<String[]> csvLines, List<ComputationRule> rulesAlreadyProcessed) {
		String rule = row[0];
		return switch (rule) {
			case "+" -> new SumRule(row);
			case "-" -> new SubstractRule(row);
			case "*" -> new MultiplyRule(row);
			case "/" -> new DivideRule(row);
			case "%" -> new ModuloRule(row);
			case "[2:]+[1:]=[:1]" -> new TwoNumbersSum(rulesAlreadyProcessed);
			case "[2:]*[1:]=[:1]" -> new TwoNumbersMultiplication(rulesAlreadyProcessed);
			case "[1:1]*[1:2]=[:2]" -> new FirstRowTwoNumbersMultiplicationToSecondPosition(csvLines);
			case "[5:]=[:3]" -> new NumberToThirdPosition(rulesAlreadyProcessed);
			case "[1:1]=[:1],[3:1]=[:2],[6:1]=[:3]" -> new NumbersInAllPositions(csvLines);
			default -> throw new ComputeException("Rule for " + rule + " is not implemented");
		};
	}

	public List<ComputationRule> computeTotal(List<ComputationRule> rules) {
		int total3 = 0;
		int total2 = 0;
		int total1 = 0;
		for (ComputationRule rule : rules) {
			total3 += rule.getResults().get(2);
			total2 += rule.getResults().get(1);
			total1 += rule.getResults().get(0);
		}
		rules.add(new TotalRule(total1, total2, total3));
		rules.add(new TotalRule(total1, total2 + total3, 0));
		rules.add(new TotalRule(total1 + total2 + total3, 0, 0));
		return rules;
	}
}

package com.ptc.computationNew;

import com.ptc.computationNew.impl.custom.*;
import com.ptc.computationNew.impl.simple.*;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

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
			default -> throw new NotImplementedException("Rule for " + rule + " is not implemented");
		};
	}
}

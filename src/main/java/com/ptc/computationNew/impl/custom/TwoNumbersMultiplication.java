package com.ptc.computationNew.impl.custom;

import com.ptc.computationNew.ComputationRule;
import com.ptc.computationNew.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[2:]*[1:]=[:1]")
public class TwoNumbersMultiplication extends CustomComputationRule {

	public TwoNumbersMultiplication(List<ComputationRule> rulesAlreadyProcessed) {
		List<Integer> numbers = new ArrayList<>(2);
		numbers.add(rulesAlreadyProcessed.get(1).getResults().get(0));
		numbers.add(rulesAlreadyProcessed.get(0).getResults().get(0));
		this.setResults(List.of(numbers.get(0) * numbers.get(1)));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;" + this.getResults().get(0) + ";0;0";
	}
}

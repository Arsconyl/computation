package com.ptc.computation.impl.custom;

import com.ptc.computation.ComputationRule;
import com.ptc.computation.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[2:]+[1:]=[:1]")
public class TwoNumbersSum extends CustomComputationRule {

	public TwoNumbersSum(List<ComputationRule> rulesAlreadyProcessed) {
		List<Integer> numbers = new ArrayList<>(2);
		numbers.add(rulesAlreadyProcessed.get(1).getResults().get(0));
		numbers.add(rulesAlreadyProcessed.get(0).getResults().get(0));
		this.setResults(List.of(numbers.get(0) + numbers.get(1)));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;" + this.getResults().get(0) + ";0;0";
	}

}

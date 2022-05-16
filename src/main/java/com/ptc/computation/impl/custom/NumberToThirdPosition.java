package com.ptc.computation.impl.custom;

import com.ptc.computation.ComputationRule;
import com.ptc.computation.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[5:]=[:3]")
public class NumberToThirdPosition extends CustomComputationRule {

	public NumberToThirdPosition(List<ComputationRule> rulesAlreadyProcessed) {
		List<Integer> numbers = new ArrayList<>(1);
		numbers.add(rulesAlreadyProcessed.get(4).getResults().get(0));
		this.setResults(List.of(numbers.get(0)));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;" + "0;0;" + this.getResults().get(0);
	}

}

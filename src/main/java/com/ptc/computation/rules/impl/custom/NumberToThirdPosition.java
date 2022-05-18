package com.ptc.computation.rules.impl.custom;

import com.ptc.computation.rules.ComputationRule;
import com.ptc.computation.rules.CustomComputationRule;
import com.ptc.computation.rules.Description;

import java.util.ArrayList;
import java.util.List;

@Description(name="CUSTOM", value="[5:]=[:3]")
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

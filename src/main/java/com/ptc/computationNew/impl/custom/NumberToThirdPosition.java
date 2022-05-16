package com.ptc.computationNew.impl.custom;

import com.ptc.computationNew.ComputationRule;
import com.ptc.computationNew.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[5:]=[:3]")
public class NumberToThirdPosition extends CustomComputationRule {

	public NumberToThirdPosition(List<ComputationRule> rulesAlreadyProcessed) {
		List<Integer> numbers = new ArrayList<>(1);
		numbers.add(rulesAlreadyProcessed.get(4).getResults().get(0));
		this.setResults(List.of(numbers.get(0) / 3));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;" + "0;0;" + this.getResults().get(0) + "\n";
	}

}

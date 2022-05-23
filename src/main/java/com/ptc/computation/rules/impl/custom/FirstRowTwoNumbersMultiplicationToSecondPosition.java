package com.ptc.computation.rules.impl.custom;

import com.ptc.computation.rules.CustomComputationRule;
import com.ptc.computation.rules.Description;

import java.util.ArrayList;
import java.util.List;

@Description(name="CUSTOM", value="[1:1]*[1:2]=[:2]")
public class FirstRowTwoNumbersMultiplicationToSecondPosition extends CustomComputationRule {

	public FirstRowTwoNumbersMultiplicationToSecondPosition(List<String[]> csvLines) {
		List<Integer> numbers = new ArrayList<>(2);
		numbers.add(Integer.parseInt(csvLines.get(0)[1]));
		numbers.add(Integer.parseInt(csvLines.get(0)[2]));
		this.setResults(List.of(0, numbers.get(0) * numbers.get(1), 0));
	}
}

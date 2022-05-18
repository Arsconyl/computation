package com.ptc.computation.impl.custom;

import com.ptc.computation.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[1:1]*[1:2]=[:2]")
public class FirstRowTwoNumbersMultiplicationToSecondPosition extends CustomComputationRule {

	public FirstRowTwoNumbersMultiplicationToSecondPosition(List<String[]> csvLines) {
		List<Integer> numbers = new ArrayList<>(2);
		numbers.add(Integer.parseInt(csvLines.get(0)[1]));
		numbers.add(Integer.parseInt(csvLines.get(0)[2]));
		this.setResults(List.of(numbers.get(0) * numbers.get(1)));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;0;" + this.getResults().get(0) + ";0";
	}

}

package com.ptc.computationNew.impl.custom;

import com.ptc.computationNew.CustomComputationRule;
import jdk.jfr.Description;

import java.util.ArrayList;
import java.util.List;

@Description("[1:1]=[:1],[3:1]=[:2],[6:1]=[:3]")
public class NumbersInAllPositions extends CustomComputationRule {

	public NumbersInAllPositions(List<String[]> csvLines) {
		List<Integer> numbers = new ArrayList<>(3);
		numbers.add(Integer.parseInt(csvLines.get(0)[1]));
		numbers.add(Integer.parseInt(csvLines.get(2)[1]));
		numbers.add(Integer.parseInt(csvLines.get(5)[1]));
		this.setResults(List.of(numbers.get(0), numbers.get(1), numbers.get(2)));
	}

	@Override
	public String toCSVLine() {
		return "CUSTOM;" + this.getResults().get(0) + ";" + this.getResults().get(1) + ";" + this.getResults().get(2) + "\n";
	}
}

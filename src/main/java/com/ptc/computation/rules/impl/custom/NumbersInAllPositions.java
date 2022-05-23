package com.ptc.computation.rules.impl.custom;

import com.ptc.computation.rules.CustomComputationRule;
import com.ptc.computation.rules.Description;

import java.util.ArrayList;
import java.util.List;

@Description(name="CUSTOM", value="[1:1]=[:1],[3:1]=[:2],[6:1]=[:3]")
public class NumbersInAllPositions extends CustomComputationRule {

	public NumbersInAllPositions(List<String[]> csvLines) {
		List<Integer> numbers = new ArrayList<>(3);
		numbers.add(Integer.parseInt(csvLines.get(0)[1]));
		numbers.add(Integer.parseInt(csvLines.get(2)[1]));
		numbers.add(Integer.parseInt(csvLines.get(5)[1]));
		this.setResults(List.of(numbers.get(0), numbers.get(1), numbers.get(2)));
	}
}

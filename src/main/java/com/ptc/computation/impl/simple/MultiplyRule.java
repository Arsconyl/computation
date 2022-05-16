package com.ptc.computation.impl.simple;

import com.ptc.computation.SimpleComputationRule;

import java.util.List;

public class MultiplyRule extends SimpleComputationRule {

	public MultiplyRule(String[] row) {
		super(row);
		this.setResults(List.of(getNumbers().stream().reduce(1, (a, b) -> a * b)));
	}

	@Override
	public String toCSVLine() {
		return "MULTI" + ";" + this.getResults().get(0) + ";0;0";
	}
}

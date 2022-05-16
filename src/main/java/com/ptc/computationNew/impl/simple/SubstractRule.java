package com.ptc.computationNew.impl.simple;

import com.ptc.computationNew.SimpleComputationRule;

import java.util.List;

public class SubstractRule extends SimpleComputationRule {

	public SubstractRule(String[] row) {
		super(row);
		this.setResults(List.of(getNumbers().stream().reduce(0, (a, b) -> a - b)));
	}

	@Override
	public String toCSVLine() {
		return "SUB" + ";" + this.getResults().get(0) + ";0;0\n";
	}
}

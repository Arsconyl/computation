package com.ptc.computationNew.impl.simple;

import com.ptc.computationNew.SimpleComputationRule;

import java.util.List;

public class SumRule extends SimpleComputationRule {

	public SumRule(String[] row) {
		super(row);
		this.setResults(List.of(getNumbers().stream().reduce(0, Integer::sum)));
	}

	@Override
	public String toCSVLine() {
		return "ADD" + ";" + this.getResults().get(0) + ";0;0\n";
	}

}

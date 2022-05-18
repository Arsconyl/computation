package com.ptc.computation.rules.impl.simple;

import com.ptc.computation.rules.Description;
import com.ptc.computation.rules.SimpleComputationRule;

import java.util.List;

@Description(name="SUB", value="Substracts numbers")
public class SubstractRule extends SimpleComputationRule {

	public SubstractRule(String[] row) {
		super(row);
		this.setResults(List.of(getNumbers().stream().reduce(0, (a, b) -> a - b)));
	}

	@Override
	public String toCSVLine() {
		return "SUB" + ";" + this.getResults().get(0) + ";0;0";
	}
}

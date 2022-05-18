package com.ptc.computation.rules.impl.simple;

import com.ptc.computation.rules.Description;
import com.ptc.computation.rules.SimpleComputationRule;

import java.util.List;

@Description(name="MULTI", value="Multiplies numbers")
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

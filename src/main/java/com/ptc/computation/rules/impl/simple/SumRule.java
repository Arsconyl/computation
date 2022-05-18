package com.ptc.computation.rules.impl.simple;

import com.ptc.computation.rules.Description;
import com.ptc.computation.rules.SimpleComputationRule;

import java.util.List;

@Description(name="ADD", value="Adds numbers")
public class SumRule extends SimpleComputationRule {

	public SumRule(String[] row) {
		super(row);
		this.setResults(List.of(getNumbers().stream().reduce(0, Integer::sum)));
	}

	@Override
	public String toCSVLine() {
		return "ADD" + ";" + this.getResults().get(0) + ";0;0";
	}

}

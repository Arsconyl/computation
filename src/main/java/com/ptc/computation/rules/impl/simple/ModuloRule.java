package com.ptc.computation.rules.impl.simple;

import com.ptc.computation.rules.Description;
import com.ptc.computation.rules.SimpleComputationRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Description(name="MOD", value="Modulo of numbers")
public class ModuloRule extends SimpleComputationRule {

	public ModuloRule(String[] row) {
		super(row);
		int reduce = 1;
		List<Integer> numbersSorted = new ArrayList<>(List.copyOf(this.getNumbers()));
		Collections.sort(numbersSorted);
		for (Integer number : numbersSorted) {
			reduce = reduce % number;
		}
		this.setResults(List.of(reduce));
	}

	@Override
	public String toCSVLine() {
		return "MOD" + ";" + this.getResults().get(0) + ";0;0";
	}
}

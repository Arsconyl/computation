package com.ptc.computation.rules.impl.simple;

import com.ptc.computation.rules.Description;
import com.ptc.computation.rules.SimpleComputationRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Description(name="DIV", value="Divides numbers")
public class DivideRule extends SimpleComputationRule {

	public DivideRule(String[] row) {
		super(row);
		int reduce = 1;
		List<Integer> numbersSorted = new ArrayList<>(List.copyOf(this.getNumbers()));
		Collections.sort(numbersSorted);
		for (Integer number : numbersSorted) {
			reduce = number / reduce;
		}
		this.setResults(List.of(reduce, 0, 0));
	}

	@Override
	public String toCSVLine() {
		return "DIV" + ";" + this.getResults().get(0) + ";0;0";
	}
}

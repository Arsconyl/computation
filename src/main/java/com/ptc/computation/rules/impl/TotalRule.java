package com.ptc.computation.rules.impl;

import com.ptc.computation.rules.ComputationRule;

import java.util.Arrays;
import java.util.List;

public class TotalRule implements ComputationRule {

	private final List<Integer> results;

	public TotalRule(int total1, int total2, int total3) {
		this.results = Arrays.asList(total1, total2, total3);
	}

	@Override
	public String toCSVLine() {
		return "TOTAL" + ";" + this.getResults().get(0) + ";" + this.getResults().get(1) + ";" + this.getResults().get(2);
	}

	@Override
	public List<Integer> getResults() {
		return results;
	}
}

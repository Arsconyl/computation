package com.ptc.computation.rules;

import lombok.Data;

import java.util.List;

@Data
public abstract class CustomComputationRule implements ComputationRule {
	private List<String> numbers;

	private List<Integer> results;

	public String toCSVLine() {
		return "CUSTOM;" + this.getResults().get(0) + ";" + this.getResults().get(1) + ";" + this.getResults().get(2);
	}
}

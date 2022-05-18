package com.ptc.computation.rules;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public abstract class SimpleComputationRule implements ComputationRule {

	private final List<Integer> numbers;

	private List<Integer> results;

	protected SimpleComputationRule(String[] row) {
		this.numbers = Arrays.stream(row).skip(1).filter(item-> !item.isEmpty()).map(Integer::parseInt).toList();
	}
}

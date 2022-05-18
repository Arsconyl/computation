package com.ptc.computation;

import lombok.Data;

import java.util.List;

@Data
public abstract class CustomComputationRule implements ComputationRule {
	private List<String> numbers;

	private List<Integer> results;
}

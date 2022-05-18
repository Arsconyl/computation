package com.ptc.computation.rules;

import java.util.List;

public interface ComputationRule {
	String toCSVLine();
	List<Integer> getResults();
}

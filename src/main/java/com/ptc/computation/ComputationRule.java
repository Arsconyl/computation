package com.ptc.computation;

import java.util.List;

public interface ComputationRule {
	String toCSVLine();
	List<Integer> getResults();
}

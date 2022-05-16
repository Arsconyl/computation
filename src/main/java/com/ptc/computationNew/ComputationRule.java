package com.ptc.computationNew;

import java.util.List;

public interface ComputationRule {
	String toCSVLine();
	List<Integer> getResults();
}

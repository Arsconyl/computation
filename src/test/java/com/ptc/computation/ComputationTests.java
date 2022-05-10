package com.ptc.computation;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ComputationTests {

	@Test
	public void testReadFile() throws IOException {
		Computation.readCSVFile("input-file.csv");
	}
}

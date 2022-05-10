package com.ptc.computation;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Computation {

	public static void readCSVFile(String fileName) throws IOException {
		try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
			List<String[]> r = reader.readAll();
			r.forEach(x -> System.out.println(Arrays.toString(x)));
		} catch (CsvException e) {
			throw new RuntimeException(e);
		}
	}
}

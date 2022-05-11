package com.ptc.computation;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ComputationTests {

    @Test
    public void test() throws IOException {
        String fileName = "input-file.csv";

        ComputationCSVService computationCSVService = new ComputationCSVService();

        computationCSVService.readCSVFile(fileName);
    }
}

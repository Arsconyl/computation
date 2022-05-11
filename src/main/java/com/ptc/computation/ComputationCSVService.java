package com.ptc.computation;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class ComputationCSVService {
    private static final String FILENAME = "computations.csv";

    public InputStreamResource exportCSV(List<ComputationRow> rows) throws IOException {
        try (Writer excelWriter = new FileWriter(FILENAME)) {
            excelWriter.write("RULE;RESULT1,RESULT2,RESULT3\n");
            for (ComputationRow row : rows)
            {
                excelWriter.write(row.toCSV() + "\n");
            }
        }

        return new InputStreamResource(new FileInputStream(FILENAME));
    }
}



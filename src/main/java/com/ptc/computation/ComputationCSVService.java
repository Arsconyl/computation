package com.ptc.computation;

import com.ptc.computation.rules.ComputationRule;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.List;

@Service
public class ComputationCSVService {

    public InputStreamResource exportCSV(List<ComputationRule> rules) throws IOException {
        String filename = "/tmp/computation-" + Calendar.getInstance().getTimeInMillis() + ".csv";
        try (Writer excelWriter = new FileWriter(filename)) {
            excelWriter.write("RULE;RESULT1;RESULT2;RESULT3\n");
            for (ComputationRule rule : rules)
                excelWriter.write(rule.toCSVLine().concat("\n"));
        }

        return new InputStreamResource(new FileInputStream(filename));
    }
}



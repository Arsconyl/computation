package com.ptc.computation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.ComputationRule;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<String[]> csvLines(String fileName) throws IOException, CsvException {
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        Path csvFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(csvFile, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {

            List<String[]> allLines = reader.readAll();
            allLines.remove(0); // remove header
            return allLines;
        }
    }

    public File csvFile(String fileName, List<ComputationRule> rules) throws IOException {

        Path csvFile = Paths.get(fileName);

        try (BufferedWriter bw = Files.newBufferedWriter(csvFile, StandardCharsets.UTF_8)) {
            bw.write("RULE;RESULT1;RESULT2;RESULT3\n");
            for (ComputationRule rule : rules)
                bw.write(rule.toCSVLine().concat("\n"));
        }

        return new File(fileName);
    }
}



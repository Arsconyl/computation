package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ComputationService {

    public List<ComputationRow> compute(String file) throws IOException, CsvException {
        return ParsingComputation.readCSVFile(file);
    }
}

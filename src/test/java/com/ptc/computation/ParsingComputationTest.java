package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParsingComputationTest {

    @Test
    void test() throws IOException, CsvException {
        String fileName = "input-file.csv";

        List<ComputationRow> rows = ParsingComputation.readCSVFile(fileName);

        assertEquals(9, rows.size());

        Iterator<ComputationRow> iterator = rows.iterator();

        assertEquals("ADD;6;0;0", iterator.next().toCSV());
        assertEquals("ADD;10;0;0", iterator.next().toCSV());
        assertEquals("MULTI;40;0;0", iterator.next().toCSV());
        assertEquals("CUSTOM;16;0;0", iterator.next().toCSV());
        assertEquals("CUSTOM;60;0;0", iterator.next().toCSV());
        assertEquals("DIV;2;0;0", iterator.next().toCSV());
        assertEquals("CUSTOM;0;8;0", iterator.next().toCSV());
        assertEquals("CUSTOM;0;0;60", iterator.next().toCSV());
        assertEquals("CUSTOM;2;10;4", iterator.next().toCSV());

    }
}

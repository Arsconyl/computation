package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComputationServiceTest {

    @Test
    void test() throws IOException, CsvException {
        String fileName = "input-file.csv";
        
        ComputationService computationService = new ComputationService();
        
        List<ComputationRule> rules = computationService.compute(fileName);

        assertEquals(9, rules.size());

        Iterator<ComputationRule> iterator = rules.iterator();

        assertEquals("ADD;6;0;0", iterator.next().toCSVLine());
        assertEquals("ADD;10;0;0", iterator.next().toCSVLine());
        assertEquals("MULTI;40;0;0", iterator.next().toCSVLine());
        assertEquals("CUSTOM;16;0;0", iterator.next().toCSVLine());
        assertEquals("CUSTOM;60;0;0", iterator.next().toCSVLine());
        assertEquals("DIV;2;0;0", iterator.next().toCSVLine());
        assertEquals("CUSTOM;0;8;0", iterator.next().toCSVLine());
        assertEquals("CUSTOM;0;0;60", iterator.next().toCSVLine());
        assertEquals("CUSTOM;2;10;4", iterator.next().toCSVLine());

    }
}

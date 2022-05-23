package com.ptc.computation;

import com.opencsv.exceptions.CsvException;
import com.ptc.computation.rules.ComputationRule;
import com.ptc.computation.rules.ComputationRuleFactory;
import com.ptc.computation.rules.Description;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ComputationServiceTest {

    @Autowired
    ComputationService computationService;

    @Test
    void testCompute() throws IOException, CsvException {
        String fileName = "input-file.csv";

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

    @Test
    void testGetAllRules() {
        Set<Description> descriptions = computationService.getAllRules();

        assertEquals(10, descriptions.size());

        assertTrue(descriptions.stream().anyMatch(description -> description.name().equals("ADD")));
        assertTrue(descriptions.stream().anyMatch(description -> description.value().equals("Adds numbers")));

        assertTrue(descriptions.stream().anyMatch(description -> description.name().equals("MULTI")));
        assertTrue(descriptions.stream().anyMatch(description -> description.value().equals("Multiplies numbers")));

        assertTrue(descriptions.stream().anyMatch(description -> description.name().equals("CUSTOM")));
        assertTrue(descriptions.stream().anyMatch(description -> description.value().equals("[1:1]*[1:2]=[:2]")));
    }
}

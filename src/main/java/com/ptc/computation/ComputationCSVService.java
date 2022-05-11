package com.ptc.computation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ComputationCSVService {

    private Pair<Integer, Integer> parsePosition(String pos) {
        String[] positions = pos.split(":");
        String position1 = positions[0].replace("[", "");
        String position2 = "";
        if (positions.length > 1)
            position2 = positions[1].replace("]", "");
        if (position1.equals(""))
            return Pair.of(null, Integer.parseInt(position2));
        else if (position2.equals(""))
            return Pair.of(Integer.parseInt(position1), null);
        else
            return Pair.of(Integer.parseInt(position1), Integer.parseInt(position2));
    }

    private Character parseOperator(String operation) {
        if (operation.contains("+"))
            return '+';
        else if (operation.contains("-"))
            return '-';
        else if (operation.contains("*"))
            return '*';
        else if (operation.contains("/"))
            return '/';
        else if (operation.contains("%"))
            return '%';
        else
            return null;
    }

    public void readCSVFile(String fileName) throws IOException {
        List<ComputationRow> rows = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').build();

        try (BufferedReader br = Files.newBufferedReader(new File(fileName).toPath(), StandardCharsets.UTF_8);
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {

            List<String[]> allRows = reader.readAll();

            for (int i = 1; i < allRows.size(); i++) {
                if (allRows.get(i)[0].matches("^[+\\-*/%]$")) {
                    String[] row = allRows.get(i);
                    Computation computation = new Computation(row);
                    ComputationRow computationRow = new ComputationRow(
                            List.of(computation),
                            computation.getResult(),
                            null,
                            null,
                            1,
                            null,
                            null);
                    rows.add(computationRow);
                }
                else {
                    String[] operations = allRows.get(i)[0].split(",");
                    List<Computation> computations = new ArrayList<>();
                    for (String operation : operations) {
                        Character operator = parseOperator(operation);
                        String[] computationToProcess = operation.split("=");
                        Integer resultPosition = parsePosition(computationToProcess[1]).getRight();
                        String[] operands = computationToProcess[0].split("[+\\-*/%]");
                        Pair<Integer, Integer> firstOperandPosition = parsePosition(operands[0]);
                        Integer firstOperand = null;
                        Integer secondOperand = null;
                        if (firstOperandPosition.getRight() == null)
                            firstOperand = rows.get(firstOperandPosition.getLeft() - 1).getResult1();
                        else if (firstOperandPosition.getLeft() == null)
                            firstOperand = Integer.valueOf(allRows.get(firstOperandPosition.getRight())[2]);
                        else
                            firstOperand = Integer.valueOf(allRows.get(firstOperandPosition.getLeft())[firstOperandPosition.getRight()]);

                        if (operands.length == 2) {
                            Pair<Integer, Integer> secondOperandPosition = parsePosition(operands[1]);
                            if (secondOperandPosition.getRight() == null)
                                secondOperand = rows.get(secondOperandPosition.getLeft() - 1).getResult1();
                            else if (secondOperandPosition.getLeft() == null)
                                secondOperand = Integer.valueOf(allRows.get(secondOperandPosition.getRight())[2]);
                            else
                                secondOperand = Integer.valueOf(allRows.get(secondOperandPosition.getLeft())[secondOperandPosition.getRight()]);
                        }
                        Computation computation = null;
                        if (secondOperand == null && firstOperand != null)
                            computation = new Computation(
                                    List.of(firstOperand), null, firstOperand, resultPosition);
                        else if (secondOperand != null && firstOperand != null)
                            computation = new Computation(
                                    List.of(firstOperand, secondOperand), operator, resultPosition);

                        computations.add(computation);
                    }

                    ComputationRow computationRow = new ComputationRow(computations);
                    rows.add(computationRow);
                }
            }
        }
        catch (CsvException e) {
            throw new RuntimeException(e);
        }
        System.out.println(rows);
    }
}

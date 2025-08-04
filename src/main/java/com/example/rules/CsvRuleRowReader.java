package com.example.rules;

import java.io.*;
import java.util.*;

public class CsvRuleRowReader implements RuleRowReader {
    private final File csvFile;

    public CsvRuleRowReader(File csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public List<RuleRow> read() throws IOException {
        List<RuleRow> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5 || !parts[5].contains("EJ")) continue;
                String ruleName = parts[1] + "_" + parts[2];
                String procedureCategory = parts[3];
                String declarationType = parts[4];
                String condition1 = parts[5];
                String condition2 = parts.length > 6 ? parts[6] : null;
                String error = parts.length > 7 ? parts[7] : null;
                rows.add(new RuleRow(ruleName, procedureCategory, declarationType, new String[]{condition1, condition2}, error));
            }
        }
        return rows;
    }
}
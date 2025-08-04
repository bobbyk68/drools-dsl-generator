package com.example.rules;

import java.io.File;
import java.util.List;

public class ExcelToDslLauncher {
    public static void main(String[] args) throws Exception {
        File excelFile = new File("rules.xlsx");
        File csvFile = new File("temp.csv");

        new ExcelToCsvService().convert(excelFile, csvFile);
        RuleRowReader reader = new CsvRuleRowReader(csvFile);
        List<RuleRow> rules = reader.read();

        DslDslrGenerator generator = new DslDslrGenerator(rules);
        generator.generate(new File("output/rules.dsl"), new File("output/rules.dslr"));

        System.out.println("Rules generated via launcher.");
    }
}
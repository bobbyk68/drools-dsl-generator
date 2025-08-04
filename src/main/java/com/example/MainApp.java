package com.example;

import com.example.rules.*;

import java.io.File;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: --type=csv|excel --input=path-to-file");
            return;
        }

        String type = args[0].split("=")[1].trim();
        String inputPath = args[1].split("=")[1].trim();
        File input = new File(inputPath);

        RuleRowReader reader;
        if (type.equalsIgnoreCase("excel")) {
            File tempCsv = new File("temp.csv");
            new ExcelToCsvService().convert(input, tempCsv);
            reader = new CsvRuleRowReader(tempCsv);
        } else {
            reader = new CsvRuleRowReader(input);
        }

        List<RuleRow> rules = reader.read();
        DslDslrGenerator generator = new DslDslrGenerator(rules);
        generator.generate(new File("output/rules.dsl"), new File("output/rules.dslr"));

        System.out.println("Rules generated successfully using MainApp.");
    }
}
package com.example.rules;

import java.io.*;
import java.util.List;

public class DslDslrGenerator {
    private final List<RuleRow> rules;

    public DslDslrGenerator(List<RuleRow> rules) {
        this.rules = rules;
    }

    public void generate(File dslFile, File dslrFile) throws IOException {
        try (PrintWriter dsl = new PrintWriter(dslFile);
             PrintWriter dslr = new PrintWriter(dslrFile)) {
            for (RuleRow rule : rules) {
                dsl.println("[when]Condition " + rule.getRuleName() + " = " + rule.getConditions()[0]);
                dslr.println("rule \"\" + rule.getRuleName() + \"\"");
                dslr.println("when");
                dslr.println("    Condition " + rule.getRuleName());
                dslr.println("then");
                dslr.println("    System.out.println(\"\" + rule.getError() + \"\");");
                dslr.println("end ");
            }
        }
    }
}
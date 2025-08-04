package com.example.rules;

public class RuleRow {
    private final String ruleName;
    private final String procedureCategory;
    private final String declarationType;
    private final String[] conditions;
    private final String error;

    public RuleRow(String ruleName, String procedureCategory, String declarationType, String[] conditions, String error) {
        this.ruleName = ruleName;
        this.procedureCategory = procedureCategory;
        this.declarationType = declarationType;
        this.conditions = conditions;
        this.error = error;
    }

    public String getRuleName() { return ruleName; }
    public String getProcedureCategory() { return procedureCategory; }
    public String getDeclarationType() { return declarationType; }
    public String[] getConditions() { return conditions; }
    public String getError() { return error; }
}
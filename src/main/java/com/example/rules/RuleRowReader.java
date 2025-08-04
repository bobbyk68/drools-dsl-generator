package com.example.rules;

import java.io.IOException;
import java.util.List;

public interface RuleRowReader {
    List<RuleRow> read() throws IOException;
}
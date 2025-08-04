package com.example.rules;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelToCsvService {

    private static final Pattern ERROR_CODE_PATTERN = Pattern.compile("Error code: (DMS\\d{5})", Pattern.CASE_INSENSITIVE);

    public void convert(File inputExcel, File outputCsv) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(inputExcel));
             PrintWriter writer = new PrintWriter(new FileWriter(outputCsv))) {

            Sheet sheet = workbook.getSheetAt(2); // ✅ Sheet 3

            for (Row row : sheet) {
                if (row.getPhysicalNumberOfCells() < 10) continue;

                // ✅ Extract relevant cells
                String namePart1 = getCell(row, 1);
                String namePart2 = getCell(row, 2);
                String procCategory = getCell(row, 3);
                String declType = getCell(row, 4);
                String pseudocode = getCell(row, 5);
                String comments = getCell(row, 9);

                if (namePart1.isEmpty() || namePart2.isEmpty() || pseudocode.isEmpty() || !comments.contains("EJ")) continue;

                String ruleName = namePart2 + "_" + namePart1;

                String[] lines = pseudocode.split("\\n");
                String condition1 = "", condition2 = "", errorMsg = "";

                boolean started = false;
                int cnt = 0;

                for (String line : lines) {
                    String trimmed = line.trim();

                    // ✅ Error Code
                    Matcher matcher = ERROR_CODE_PATTERN.matcher(trimmed);
                    if (matcher.find()) {
                        errorMsg = matcher.group(1);
                    }

                    if (!started && trimmed.toLowerCase().startsWith("if")) {
                        started = true;
                    }

                    if (!started) {
                        cnt++;
                        continue;
                    }

                    if (trimmed.toLowerCase().startsWith("if")) {
                        condition1 = lines[cnt + 1].replaceAll("\\r", "").trim();  // the next line
                    } else if (trimmed.toLowerCase().startsWith("then")) {
                        condition2 = lines[cnt + 1].replaceAll("\\r", "").trim(); // the next line
                    }

                    cnt++;
                }

                // ✅ Output to CSV
                writer.println(String.join(",", List.of(
                        namePart1,
                        namePart2,
                        procCategory,
                        declType,
                        condition1,
                        condition2,
                        errorMsg
                )));
            }
        }
    }

    private String getCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim().replaceAll(",", " ");
    }
}
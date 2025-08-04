package com.example.rules;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelToCsvService {
    public void convert(File inputExcel, File outputCsv) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(inputExcel));
             PrintWriter writer = new PrintWriter(new FileWriter(outputCsv))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                StringBuilder line = new StringBuilder();
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    line.append(cell.getStringCellValue().replaceAll(",", " ")).append(",");
                }
                writer.println(line.substring(0, line.length() - 1));
            }
        }
    }
}
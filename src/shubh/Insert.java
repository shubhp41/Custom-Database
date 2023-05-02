package shubh;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Insert {
    @Serial
    tableExists Te = new tableExists();
    public  void insert(String tableName, String[] columnNames, String[] values) {
        if (!Te.tableExists(tableName)) {
            System.out.println("Error: Table " + tableName + " does not exist.");
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tableName + ".sp"));
            File file = new File(tableName + ".sp");
            FileWriter writer = new FileWriter(file, true);
            if (columnNames.length != values.length) {
                System.out.println("Error: Number of columns and values do not match.");
                return;
            }

            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            String header = "";
            if (file.length() == 0) {
                // If file is empty, create header row
                header = String.join(",", columnNames);
                writer.write(header);
            } else {
                // If file is not empty, read header row
                Scanner scanner = new Scanner(file);
                header = scanner.nextLine();
            }

            // Check that all columns in the INSERT statement exist in the table
            String[] headerColumns = header.split(",");
            for (String column : columnNames) {
                if (!Arrays.asList(headerColumns).contains(column)) {
                    System.out.println("Error: Column " + column + " not found in table " + tableName);
                    return;
                }
            }

            // Build the row to insert
            List<String> row = new ArrayList<String>();
            for (String column : headerColumns) {
                int index = Arrays.asList(columnNames).indexOf(column);
                if (index >= 0) {
                    row.add(values[index]);
                } else {
                    row.add("");
                }
            }
            String newRow = String.join(",", row);

            writer.write("\n"+newRow);


            writer.close();

            System.out.println("1 row inserted into " + tableName + ".");
        } catch (IOException e) {
            System.out.println("Error inserting row into " + tableName + ": " + e.getMessage());
        }
    }
}

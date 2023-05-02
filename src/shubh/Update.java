package shubh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Update {
    @Serial
    static String header;
    public static void update(String tableName, String setClause, String whereClause) {
        try {

            // Verify that the table exists
            File file = new File(tableName + " .sp");
            if (!file.exists()) {
                System.out.println("Error: Table " + tableName + " does not exist.");
                return;
            }

            // Parse the setClause to extract the column name and new value
            String[] setParts = setClause.split("=");
            String setColumnName = setParts[0];
            String setColumnValue = setParts[1].replaceAll("'", "");

            // Read the existing records from the file
            List<String> records = new ArrayList<>();

            try (Scanner scanner = new Scanner(file)) {
                header = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    records.add(scanner.nextLine());
                }
            }

            // Write the updated records to a new file
            FileWriter writer = new FileWriter(tableName + "_temp.sp");
            writer.write(header + "\n");
            for (String record : records) {
                String[] values = record.split(",");
                if (shouldUpdateRecord(values, setColumnName, setColumnValue, whereClause)) {
                    // Update the record with the new value
                    values[1] = setColumnValue;
                    writer.write(String.join(",", values) + "\n");
                    System.out.println("Record updated successfully.");
                } else {
                    writer.write(record + "\n");
                }
            }
            writer.close();

            // Replace the original file with the updated file
            file.delete();
            File tempFile = new File(tableName + "_temp.sp");
            tempFile.renameTo(file);
        } catch (IOException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
    public static boolean shouldUpdateRecord(String[] values, String setColumnName, String setColumnValue, String whereClause) {
        String whereColumnName = whereClause.split(" ")[0];
        String whereOperator = whereClause.split(" ")[1];
        String whereColumnValue = whereClause.split(" ")[2].replaceAll("'", "");

        int columnIdx = -1;
        for (int i = 0; i < values.length; i++) {
            if (header.split(",")[i].equals(whereColumnName)) {
                columnIdx = i;
                break;
            }
        }

        if (columnIdx == -1) {
            return false;
        }

        String value = values[columnIdx].replaceAll("'", "");
        if (whereOperator.equals("=")) {
            if (value.equals(whereColumnValue)) {
                return true;
            }
        } else if (whereOperator.equals("<")) {
            if (Integer.parseInt(value) < Integer.parseInt(whereColumnValue)) {
                return true;
            }
        } else if (whereOperator.equals(">")) {
            if (Integer.parseInt(value) > Integer.parseInt(whereColumnValue)) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

}

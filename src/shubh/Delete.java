package shubh;

import javax.management.ConstructorParameters;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Delete {
    @Serial
    public static void delete(String tableName, String whereClause) {
        try {
            List<String> rows = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(tableName + " .sp"));
            String currentLine = reader.readLine();
            String[] columns = currentLine.split(",");

            // Add column names to the rows list
            rows.add(currentLine);

            // Read each row from the file and add it to the rows list
            while ((currentLine = reader.readLine()) != null) {
                rows.add(currentLine);
            }
            reader.close();

            // Get the index of the column to filter by, if a WHERE clause was provided
            int filterColumnIndex = -1;
            String filterValue = null;
            if (whereClause != null) {
                String[] whereParts = whereClause.split(" ");
                String filterColumn = whereParts[0];
                String filterOperator = whereParts[1];
                filterValue = whereParts[2];
                filterColumnIndex = Arrays.asList(columns).indexOf(filterColumn);
            }

            // Filter rows based on the WHERE clause, if provided
            List<String> filteredRows = new ArrayList<>();
            for (String row : rows) {
                if (filterColumnIndex == -1) {
                    // No filter, add row to filteredRows
                    filteredRows.add(row);
                } else {
                    String[] values = row.split(",");
                    if (values[filterColumnIndex].equals(filterValue)) {
                        // This is the row to be deleted, don't add it to filteredRows
                    } else {
                        // Add all other rows to filteredRows
                        filteredRows.add(row);
                    }
                }
            }

            // Write the filtered rows back to the file
            FileWriter writer = new FileWriter(tableName + " .sp");
            for (String row : filteredRows) {
                writer.write(row + "\n");
            }
            writer.close();

            System.out.println("Row deleted.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}

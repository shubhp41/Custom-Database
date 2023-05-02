package shubh;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Scanner;

public class Select {
    @Serial
    public static void select(String tableName, String columns, String whereClause) {

        try {
            File file = new File(tableName + " .sp");
            Scanner scanner = new Scanner(file);

            String[] selectedColumns;
            if (columns.equals("*")){
                String header = scanner.nextLine();
                String[] headerColumns = header.split(",");
                selectedColumns = headerColumns;
                int[] columnIndexes = new int[selectedColumns.length];


                for (int i = 0; i < selectedColumns.length; i++) {
                    String selectedColumn = selectedColumns[i];
                    int index = -1;
                    for (int j = 0; j < headerColumns.length; j++) {
                        if (headerColumns[j].equals(selectedColumn)) {
                            index = j;
                            break;
                        }
                    }
                    if (index == -1) {
                        System.out.println("Error: Column " + selectedColumn + " not found in table " + tableName);
                        return;
                    }
                    columnIndexes[i] = index;
                }
                for (int k = 0; k < headerColumns.length; k++) {
                    System.out.print(headerColumns[k] + "   ");
                }
                System.out.println();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] values = line.split(",");
                    if (whereClause == null) {
                        for (int i = 0; i < columnIndexes.length; i++) {
                            System.out.print(values[columnIndexes[i]] + "   ");
                        }
                        System.out.println();
                    } else {
                        String[] whereParts = whereClause.split(" ");
                        String whereColumn = whereParts[0];
                        String operator = whereParts[1];
                        String value = whereParts[2];
                        int whereColumnIndex = -1;
                        for (int i = 0; i < headerColumns.length; i++) {
                            if (headerColumns[i].equals(whereColumn)) {
                                whereColumnIndex = i;
                                break;
                            }
                        }
                        if (whereColumnIndex == -1) {
                            System.out.println("Error: Column " + whereColumn + " not found in table " + tableName);
                            return;
                        }
                        if (operator.equals("=")) {
                            if (values[whereColumnIndex].equals(value)) {
                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");
                                }
                                System.out.println();
                            }
                        } else if (operator.equals(">")) {
                            int whereValue = Integer.parseInt(value);
                            int actualValue = Integer.parseInt(values[whereColumnIndex]);
                            if (actualValue > whereValue) {
                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");
                                }
                                System.out.println();
                            }
                        } else if (operator.equals("<")) {
                            int whereValue = Integer.parseInt(value);
                            int actualValue = Integer.parseInt(values[whereColumnIndex]);
                            if (actualValue < whereValue) {
                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");
                                }
                                System.out.println();
                            }
                        }
                    }
                }
            }
            else{
                selectedColumns = columns.split(",");
                int[] columnIndexes = new int[selectedColumns.length];

                String header = scanner.nextLine();
                String[] headerColumns = header.split(",");
                for (int i = 0; i < selectedColumns.length; i++) {
                    String selectedColumn = selectedColumns[i];
                    int index = -1;
                    for (int j = 0; j < headerColumns.length; j++) {
                        if (headerColumns[j].equals(selectedColumn)) {
                            index = j;
                            break;
                        }
                    }
                    if (index == -1) {
                        System.out.println("Error: Column " + selectedColumn + " not found in table " + tableName);
                        return;
                    }
                    columnIndexes[i] = index;
                }

                for (int k = 0; k < selectedColumns.length; k++) {
                    System.out.print(selectedColumns[k] + "   ");
                }
                System.out.println();
                while (scanner.hasNextLine()) {

                    String line = scanner.nextLine();
                    String[] values = line.split(",");
                    if (whereClause == null) {
                        for (int i = 0; i < columnIndexes.length; i++) {
                            System.out.print(values[columnIndexes[i]] + "   ");
                        }
                        System.out.println();
                    } else {
                        String[] whereParts = whereClause.split(" ");
                        String whereColumn = whereParts[0];
                        String operator = whereParts[1];
                        String value = whereParts[2];
                        int whereColumnIndex = -1;
                        for (int i = 0; i < headerColumns.length; i++) {
                            if (headerColumns[i].equals(whereColumn)) {
                                whereColumnIndex = i;
                                break;
                            }
                        }
                        if (whereColumnIndex == -1) {
                            System.out.println("Error: Column " + whereColumn + " not found in table " + tableName);
                            return;
                        }
                        if (operator.equals("=")) {
                            if (values[whereColumnIndex].equals(value)) {
                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");

                                }
                                System.out.println();

                            }
                        } else if (operator.equals(">")) {
                            int whereValue = Integer.parseInt(value);
                            int actualValue = Integer.parseInt(values[whereColumnIndex]);

                            if (actualValue > whereValue) {

                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");
                                }
                                System.out.println();
                            }
                        } else if (operator.equals("<")) {
                            int whereValue = Integer.parseInt(value);
                            int actualValue = Integer.parseInt(values[whereColumnIndex]);
                            if (actualValue < whereValue) {
                                for (int i = 0; i < columnIndexes.length; i++) {
                                    System.out.print(values[columnIndexes[i]] + "   ");
                                }
                                System.out.println();
                            }
                        }
                    }
                }
            }


        } catch (IOException e) {
            System.out.println("Error selecting from table " + tableName + ": " + e.getMessage());
        }
    }

}

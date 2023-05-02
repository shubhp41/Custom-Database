package shubh;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateTable {
    @Serial
    public static void createTable(String tableName, String columns) {
        try {
            File file = new File(tableName + ".sp");
            FileWriter writer = new FileWriter(file);
            writer.write(String.join(",", columns));
            writer.close();
            System.out.println("Table " + tableName + " created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating table " + tableName + ": " + e.getMessage());
        }
    }
}

package shubh;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class user {
    private Map<String, String[]> users = new HashMap<>();
    private File usersFile = new File("users.sp");
    CreateTable ct = new CreateTable();
    Insert insertRecord = new Insert();
    Select selectRecord = new Select();
    Update updateRecord = new Update();
    Delete deleteRecord = new Delete();
    public void loadUsers() {
        if (usersFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(usersFile));
                String line = reader.readLine();
                while (line != null) {
                    String[] fields = line.split(",");
                    if (fields.length >= 3) {
                        users.put(fields[0], new String[] { fields[1], fields[2], fields[3] });
                    }
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error loading users from file: " + e.getMessage());
            }
        }
    }

    public void saveUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile));
            for (String username : users.keySet()) {
                String[] userInfo = users.get(username);
                writer.write(username + "," + userInfo[0] + "," + userInfo[1] + "," + userInfo[2] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public void createUser(Scanner scanner) {
        System.out.println("Enter a username:");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists, please try again.");
            return;
        }
        System.out.println("Enter a password:");
        String password = scanner.nextLine();
        System.out.println("Enter a security question:");
        String securityQuestion = scanner.nextLine();
        System.out.println("Enter the security answer:");
        String securityAnswer = scanner.nextLine();
        users.put(username, new String[] { password, securityQuestion, securityAnswer });
        try {
            FileWriter writer = new FileWriter(usersFile, true);
            writer.write(username + "," + password + "," + securityQuestion + "," + securityAnswer + "\n");
            writer.close();

            System.out.println("User created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing user to file: " + e.getMessage());
        }

    }

    public void loginUser(Scanner scanner) {

        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("Username not found, please try again.");
            return;
        }
        String[] userInfo = users.get(username);
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        if (!password.equals(userInfo[0])) {
            System.out.println("Incorrect password, please try again.");
            return;
        }
        System.out.println("Security question: " + userInfo[1]);
        System.out.println("Enter the security answer:");
        String securityAnswer = scanner.nextLine();
        if (!securityAnswer.equals(userInfo[2])) {
            System.out.println("Incorrect security answer, please try again.");
            return;
        }
        System.out.println("Login Successfully!");
        System.out.println("Welcome to the Lightweight Database!");
        while (true) {
            System.out.print("> ");
            String query = scanner.nextLine().trim();

            // Exit loop if user enters "exit"
            if (query.equals("exit")) {
                break;
            }

            // Use regular expressions to match queries and extract relevant information
            String createTableRegex = "^CREATE TABLE (.*)";
            String selectRegex = "^SELECT (.*) FROM (.*) WHERE (.*)";
            String insertRegex = "^insert into (.*) values (.*)";
            String updateRegex = "^UPDATE (.*) SET (.*) WHERE (.*)";
            String deleteRegex = "DELETE FROM (.*) WHERE (.*)";

            Pattern createTablePattern = Pattern.compile(createTableRegex);
            Pattern selectPattern = Pattern.compile(selectRegex);
            Pattern insertPattern = Pattern.compile(insertRegex);
            Pattern updatePattern = Pattern.compile(updateRegex);
            Pattern deletePattern = Pattern.compile(deleteRegex);

            Matcher createTableMatcher = createTablePattern.matcher(query);
            Matcher selectMatcher = selectPattern.matcher(query);
            Matcher insertMatcher = insertPattern.matcher(query);
            Matcher updateMatcher = updatePattern.matcher(query);
            Matcher deleteMatcher = deletePattern.matcher(query);

            if (createTableMatcher.matches()) {
                // User entered a CREATE TABLE query
                // extract text inside parentheses
                String tableName = createTableMatcher.group(1).split("\\(")[0];
                String temp = createTableMatcher.group(1).split("\\(")[1];
                String columns = temp.split("\\)")[0];
                ct.createTable(tableName, columns);
            }
            else if (selectMatcher.matches()) {
                // User entered a SELECT query
                String columns = selectMatcher.group(1);
                String tableName = selectMatcher.group(2);
                String whereClause = selectMatcher.group(3);
                selectRecord.select(tableName, columns, whereClause);
            }
            else if (insertMatcher.matches()) {
                // User entered an INSERT query
                String tableName = insertMatcher.group(1).split("\\(")[0];
                String temp = insertMatcher.group(1).split("\\(")[1];
                String aa = temp.split("\\)")[0];
                String[] columnNames = aa.split(",");
                String temp1 = insertMatcher.group(2).split("\\(")[1];
                String a = temp1.split("\\)")[0];
                String[] values = a.split(",");
                insertRecord.insert(tableName, columnNames, values);
            }
            else if (updateMatcher.matches()) {
                // User entered an UPDATE query
                String tableName = updateMatcher.group(1);
                String setClause = updateMatcher.group(2);
                String whereClause = updateMatcher.group(3);
                updateRecord.update(tableName, setClause, whereClause);
            }else if (deleteMatcher.matches()) {
                // User entered a DELETE query
                String tableName = deleteMatcher.group(1);
                String whereClause = deleteMatcher.group(2);
                deleteRecord.delete(tableName, whereClause);
            }
            else {
                System.out.println("Invalid query. Please try again.");
            }
        }

    }

}

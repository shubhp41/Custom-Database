import shubh.CreateTable;
import shubh.user;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        user db = new user();

        db.loadUsers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the lightweight database!");

        while (true) {
            System.out.println("Enter 1 to create a new user");
            System.out.println("Enter 2 to login");
            System.out.println("Enter 3 to exit");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    db.createUser(scanner);
                    break;
                case "2":
                    db.loginUser(scanner);
                    break;
                case "3":
                    db.saveUsers();
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid input, please try again.");
            }
        }


    }
}
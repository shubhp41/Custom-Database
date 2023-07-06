## Custom Database Management System (DBMS) - Java

This project implements a custom database management system (DBMS) in Java, providing users with the ability to register and log in using two-factor authentication (2FA). The system incorporates a randomized security question from 2FA to verify users during the login process. Once logged in, users can create multiple tables within their database, which is fully managed using the filesystem.

### Features

1. Two-Factor Authentication (2FA): Users are required to register using 2FA, adding an extra layer of security to their accounts.
2. Randomized Security Questions: The system generates a random security question from 2FA to verify users during the login process, enhancing authentication.
3. Database Creation: Upon successful login, users can create multiple tables within their personal database, providing them with a customizable data storage solution.
4. Filesystem Management: The database is managed entirely using the filesystem, ensuring easy storage, retrieval, and backup of table data.
5. CRUD Operations: Users have the capability to perform CRUD operations (Create, Read, Update, Delete) on their tables. They can insert, update, and delete rows and columns using real SQL queries, enabling data manipulation as needed.

### Usage

1. Clone the repository: `git clone https://github.com/your-username/custom-database.git`
2. Compile the Java files: `javac *.java`
3. Run the application: `java Main`

### Requirements

- Java 8 or higher

### File Structure

- `Main.java`: The entry point of the application.
- `Database.java`: Handles database creation, table management, and CRUD operations.
- `User.java`: Represents a user with login credentials and 2FA security.
- `Table.java`: Represents a table within a user's database, allowing data manipulation.

### Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.


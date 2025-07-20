import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import Secret;

public class ReadCSV {
    public static void main(String[] args) {
        String filePath = "employees.csv";
        String jdbcURL = Secret.JDBC_URL;
        String username = Secret.DB_USER;
        String password = Secret.DB_PASSWORD;

        try (
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            Connection connection = DriverManager.getConnection(jdbcURL, username, password)
        ) {
            String line;
            br.readLine(); 

            String insertSQL = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            String checkSQL = "SELECT emp_id FROM Employee WHERE emp_id = ?";

            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
            PreparedStatement checkStmt = connection.prepareStatement(checkSQL);

            Statement stmt = connection.createStatement();
            try {
                stmt.executeUpdate("TRUNCATE TABLE Employee");
                System.out.println("Table Employee truncated.");
            } catch (SQLException e) {
                System.out.println("Table Employee does not exist.");
            }
            stmt.close();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 8) {
                    System.out.println("Skipping incomplete row: " + line);
                    continue;
                }

                try {
                    int empId = Integer.parseInt(values[0].trim());
                    String firstName = values[1].trim();
                    String lastName = values[2].trim();
                    String email = values[3].trim();
                    String phone = values[4].trim();
                    String department = values[5].trim();
                    double salary = Double.parseDouble(values[6].trim());
                    Date joinDate = Date.valueOf(values[7].trim()); 

                    // Check if emp_id already exists
                    checkStmt.setInt(1, empId);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        System.out.println("Duplicate Entry");
                        continue;
                    }

                    // Inserting
                    insertStmt.setInt(1, empId);
                    insertStmt.setString(2, firstName);
                    insertStmt.setString(3, lastName);
                    insertStmt.setString(4, email);
                    insertStmt.setString(5, phone);
                    insertStmt.setString(6, department);
                    insertStmt.setDouble(7, salary);
                    insertStmt.setDate(8, joinDate);

                    insertStmt.executeUpdate();
                    System.out.println("Inserted emp_id: " + empId);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format in row: " + line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format in row: " + line);
                } catch (SQLException e) {
                    System.out.println("SQL error for row: " + line);
                    e.printStackTrace();
                }
            }

            System.out.println("\nImport completed.");

        } catch (IOException | SQLException e) {
            System.out.println("Some Error Occured");
            e.printStackTrace();
        }
    }
}

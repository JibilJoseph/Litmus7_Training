import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class ReadCSV {
    public static void main(String[] args) {
        String filePath = "employees.csv"; 
        String jdbcURL = "jdbc:mysql://localhost:3306/java"; 
        String username = "root"; 
        String password = "root"; 

        try (
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            Connection connection = DriverManager.getConnection(jdbcURL, username, password)
        ) {
            String line;

            // Skip the header line
            br.readLine();

            String sql = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Skip incomplete rows
                if (values.length < 8) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    statement.setInt(1, Integer.parseInt(values[0].trim()));
                    statement.setString(2, values[1].trim());
                    statement.setString(3, values[2].trim());
                    statement.setString(4, values[3].trim());
                    statement.setString(5, values[4].trim());
                    statement.setString(6, values[5].trim());
                    statement.setDouble(7, Double.parseDouble(values[6].trim()));
                    statement.setDate(8, Date.valueOf(values[7].trim()));

                    statement.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error inserting row: " + line);
                    ex.printStackTrace();
                }
            }

            System.out.println("Data inserted successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

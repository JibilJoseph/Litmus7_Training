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
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        ) {
            String line;

            String sql = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Skip if the row is not complete
                if (values.length < 8) continue;

                statement.setInt(1, Integer.parseInt(values[0]));
                statement.setString(2, values[1]);
                statement.setString(3, values[2]);
                statement.setString(4, values[3]);
                statement.setString(5, values[4]);
                statement.setString(6, values[5]);
                statement.setDouble(7, Double.parseDouble(values[6]));
                statement.setDate(8, Date.valueOf(values[7])); // yyyy-MM-dd format

                statement.executeUpdate();
            }

            System.out.println("Data inserted successfully.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
    public static void main(String[] args) {
        String filePath = "employees.csv"; // Path to your CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] values = line.split(",");
                // Store values to variables (example for 3 columns)
                String emp_id = values[0];
                String first_name = values[1];
                String last_name = values[2];
                String email = values[3];
                String phone = values[4];
                String department = values[5];
                String salary = values[6];
                String join_date = values[7];

                // Print the row
                System.out.println(emp_id+first_name+last_name+email+phone+department+salary+join_date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
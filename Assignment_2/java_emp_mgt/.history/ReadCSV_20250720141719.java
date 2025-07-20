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
                String column2 = values[1];
                String column3 = values[2];
                String column4 = values[3];
                String column5 = values[4];
                String column6 = values[5];
                String column7 = values[6];
                String column8 = values[7];

                // Print the row
                System.out.println(column1+column2+column3+column4+column5+column6+column7+column8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
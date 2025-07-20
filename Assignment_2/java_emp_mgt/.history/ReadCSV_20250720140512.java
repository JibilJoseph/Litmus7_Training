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
                String column1 = values[0];
                String column2 = values[1];
                String column3 = values[2];

                // Print the row
                System.out.println(column1+column2+column3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EmployeeManagerController {

    //variables
    private final String filepath="employees.csv";
    private final String url="jdbc:mysql://localhost:3306/java";
    private final String username="root";
    private final String password="root";

    // get connection function
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);

    }

    //reading function

    public List<String[]> readCSV()
    {
        List<String[]> data=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(filepath)))
        {
            String line;
            //skip first line
            br.readLine();
            
            while((line=br.readLine())!=null)
            {
                String[] values=line.split(",");
                if(values.length<8)
                {
                    System.out.println("Incomplete Row, skipping: " + line);
                    continue;
                }
                data.add(values);
            }
        }
        catch(Exception e)
        {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return data; 
    }

    public void writeDataToDB()
    {
        List<String[]> employeeData=readCSV();
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            String insertSQL = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String checkSQL = "SELECT emp_id FROM Employee WHERE emp_id = ?";

            //prepare statements

            PreparedStatement insertstmt=connection.prepareStatement(insertSQL);
            PreparedStatement checkstmt=connection.prepareStatement(checkSQL);

            for(String[] values:employeeData)
            {
                try{
                    int empid=Integer.parseInt(values[0].trim());
                    String first_name=values[1].trim();
                    String last_name=values[2].trim();
                    String email=values[3].trim();
                    String phone=values[4].trim();
                    String department=values[5].trim();
                    Double salary=Double.parseDouble(values[6].trim());
                    Date join_date=Date.valueOf(values[7].trim());


                    //setting  statements

                    checkstmt.setInt(1, empid);
                    ResultSet rs=checkstmt.executeQuery();

                    if(rs.next())
                    {
                        System.out.println("Duplicate emp_id "+empid);
                        continue;
                    }

                    insertstmt.setInt(1, empid);
                    insertstmt.setString(2, first_name);
                    insertstmt.setString(3 , last_name);
                    insertstmt.setString(4, email);
                    insertstmt.setString(5, phone);
                    insertstmt.setString(6, department);
                    insertstmt.setDouble(7, salary);
                    insertstmt.setDate(8, join_date);

                    insertstmt.executeUpdate();

                    System.out.println("Inserted empid: "+empid);
                    
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing data for a row. Skipping. " + e.getMessage());
                } catch (SQLException e) {
                    System.out.println("Database error during insertion. Skipping. " + e.getMessage());
                }
                 catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("An unexpected error occurred during insertion. Skipping. " + e.getMessage());
                }
            }


            
        } catch (SQLException e) {
            System.out.println("Database connection Failed: " + e.getMessage());
        }
    }
}

package com.litmus7.empManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class EmployeeManagerController {
	
	//instance variable
	String filepath;

    

    // Constructor
    public EmployeeManagerController(String filepath)
    {
    	this.filepath=filepath;
    	
    }

    //reading function

    public List<String[]> loadData()
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

    //writing function 

    public List<Response> writeDataToDB(String path)
    {
        filepath=path;
        final String url="jdbc:mysql://localhost:3306/java";
        final String username="root";
        final String password="root";
        List<String[]> employeeData=loadData();
        List<Response> responses = new ArrayList<>();
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            String insertSQL = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String checkSQL = "SELECT emp_id FROM Employee WHERE emp_id = ?";
            PreparedStatement insertstmt=connection.prepareStatement(insertSQL);
            PreparedStatement checkstmt=connection.prepareStatement(checkSQL);
            for(String[] values:employeeData)
            {
                // Validate each field
                Response empidResp = Validator.validateEmpid(values[0]);
                if(empidResp.getCode() == 2) { responses.add(empidResp); continue; }
                int empid = empidResp.getIntValue();

                Response firstNameResp = Validator.validateFirstName(values[1]);
                if(firstNameResp.getCode() == 2) { responses.add(firstNameResp); continue; }
                String first_name = values[1].trim();

                Response lastNameResp = Validator.validateLastName(values[2]);
                if(lastNameResp.getCode() == 2) { responses.add(lastNameResp); continue; }
                String last_name = values[2].trim();

                Response emailResp = Validator.validateEmail(values[3]);
                if(emailResp.getCode() == 2) { responses.add(emailResp); continue; }
                String email = values[3].trim();

                Response phoneResp = Validator.validatePhone(values[4]);
                if(phoneResp.getCode() == 2) { responses.add(phoneResp); continue; }
                String phone = values[4].trim();

                Response deptResp = Validator.validateDepartment(values[5]);
                if(deptResp.getCode() == 2) { responses.add(deptResp); continue; }
                String department = values[5].trim();

                Response salaryResp = Validator.validateSalary(values[6]);
                if(salaryResp.getCode() == 2) { responses.add(salaryResp); continue; }
                double salary = Double.parseDouble(values[6].trim());

                Response joinDateResp = Validator.validateJoinDate(values[7]);
                if(joinDateResp.getCode() == 2) { responses.add(joinDateResp); continue; }
                java.sql.Date join_date = java.sql.Date.valueOf(values[7].trim());

                try{
                    checkstmt.setInt(1, empid);
                    ResultSet rs=checkstmt.executeQuery();
                    if(rs.next())
                    {
                        responses.add(new Response(2, "Duplicate emp_id: " + empid));
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
                    responses.add(new Response(1, empid));
                } catch (NumberFormatException e) {
                    responses.add(new Response(2, "Error parsing data for a row. Skipping. " + e.getMessage()));
                } catch (SQLException e) {
                    responses.add(new Response(2, "Database error during insertion. Skipping. " + e.getMessage()));
                } catch (Exception e) {
                    responses.add(new Response(2, "An unexpected error occurred during insertion. Skipping. " + e.getMessage()));
                }
            }
        } catch (SQLException e) {
            responses.add(new Response(2, "Database connection Failed: " + e.getMessage()));
        }
        return responses;
    }
}

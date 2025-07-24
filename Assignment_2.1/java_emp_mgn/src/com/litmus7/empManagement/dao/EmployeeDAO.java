package com.litmus7.empManagement.dao;

import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.utils.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
	// function to get connection 
	
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            DatabaseConfig.URL, 
            DatabaseConfig.USERNAME, 
            DatabaseConfig.PASSWORD
        );
    }
    
    
    // function to check whether employee exists or not
    
    public boolean employeeExists(int empId) {
        String checkEmpExists = "SELECT emp_id FROM Employee WHERE emp_id = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(checkEmpExists)) {
            
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (SQLException e) {
            System.out.println("Error checking employee existence: " + e.getMessage());
            return false;
        }
    }
    
    // function to insert an Employee

    public Response insertEmployee(Employee employee) {
        String insertSQL = "INSERT INTO Employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertSQL)) {
            
            // Check for duplicate
            if (employeeExists(employee.getEmpId())) {
                return new Response(2, "Duplicate emp_id: " + employee.getEmpId());
            }
            
            stmt.setInt(1, employee.getEmpId());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getEmail());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getDepartment());
            stmt.setDouble(7, employee.getSalary());
            stmt.setDate(8, employee.getJoinDate());
            
            stmt.executeUpdate();
            return new Response(1, employee.getEmpId());
            
        } catch (SQLException e) {
            return new Response(2, "Database error during insertion: " + e.getMessage());
        }
    }
    
    // insert a list of employees

    public List<Response> insertEmployees(List<Employee> employees) {
        List<Response> responses = new ArrayList<>();
        
        for (Employee employee : employees) {
            responses.add(insertEmployee(employee));
        }
        
        return responses;
    }
}
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

    // ==================== CREATE OPERATIONS ====================

    // function to check whether employee exists or not
    public boolean employeeExists(int empId) {
        String checkEmpExists = "SELECT emp_id FROM Employee WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(checkEmpExists)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false; // Assume doesn't exist if error occurs
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

    // ==================== READ OPERATIONS ====================

    // Get employee by ID
    public Employee getEmployeeById(int empId) {
        String selectSQL = "SELECT * FROM Employee WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectSQL)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getDate("join_date")
                );
            }
            return null; // Employee not found

        } catch (SQLException e) {
            return null; // Return null if error occurs
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM Employee ORDER BY emp_id";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectAllSQL)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getDate("join_date")
                );
                employees.add(employee);
            }

        } catch (SQLException e) {
            // Return empty list if error occurs
        }

        return employees;
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        String selectByDeptSQL = "SELECT * FROM Employee WHERE department = ? ORDER BY emp_id";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectByDeptSQL)) {

            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getDate("join_date")
                );
                employees.add(employee);
            }

        } catch (SQLException e) {
            // Return empty list if error occurs
        }

        return employees;
    }

    // ==================== UPDATE OPERATIONS ====================

    // Update employee details
    public Response updateEmployee(Employee employee) {
        String updateSQL = "UPDATE Employee SET first_name = ?, last_name = ?, email = ?, " +
                          "phone = ?, department = ?, salary = ?, join_date = ? WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateSQL)) {

            // Check if employee exists
            if (!employeeExists(employee.getEmpId())) {
                return new Response(2, "Employee with ID " + employee.getEmpId() + " not found");
            }

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getDepartment());
            stmt.setDouble(6, employee.getSalary());
            stmt.setDate(7, employee.getJoinDate());
            stmt.setInt(8, employee.getEmpId());

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return new Response(1, employee.getEmpId());
            } else {
                return new Response(2, "No rows updated for employee ID: " + employee.getEmpId());
            }

        } catch (SQLException e) {
            return new Response(2, "Database error during update: " + e.getMessage());
        }
    }

    // Update employee salary only
    public Response updateEmployeeSalary(int empId, double newSalary) {
        String updateSalarySQL = "UPDATE Employee SET salary = ? WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateSalarySQL)) {

            // Check if employee exists
            if (!employeeExists(empId)) {
                return new Response(2, "Employee with ID " + empId + " not found");
            }

            stmt.setDouble(1, newSalary);
            stmt.setInt(2, empId);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return new Response(1, empId);
            } else {
                return new Response(2, "No rows updated for employee ID: " + empId);
            }

        } catch (SQLException e) {
            return new Response(2, "Database error during salary update: " + e.getMessage());
        }
    }

    // Update employee department only
    public Response updateEmployeeDepartment(int empId, String newDepartment) {
        String updateDeptSQL = "UPDATE Employee SET department = ? WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateDeptSQL)) {

            // Check if employee exists
            if (!employeeExists(empId)) {
                return new Response(2, "Employee with ID " + empId + " not found");
            }

            stmt.setString(1, newDepartment);
            stmt.setInt(2, empId);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                return new Response(1, empId);
            } else {
                return new Response(2, "No rows updated for employee ID: " + empId);
            }

        } catch (SQLException e) {
            return new Response(2, "Database error during department update: " + e.getMessage());
        }
    }

    // ==================== DELETE OPERATIONS ====================

    // Delete employee by ID
    public Response deleteEmployee(int empId) {
        String deleteSQL = "DELETE FROM Employee WHERE emp_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteSQL)) {

            // Check if employee exists before deleting
            if (!employeeExists(empId)) {
                return new Response(2, "Employee with ID " + empId + " not found");
            }

            stmt.setInt(1, empId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return new Response(1, empId);
            } else {
                return new Response(2, "No rows deleted for employee ID: " + empId);
            }

        } catch (SQLException e) {
            return new Response(2, "Database error during deletion: " + e.getMessage());
        }
    }

    // Delete employees by department
    public Response deleteEmployeesByDepartment(String department) {
        String deleteByDeptSQL = "DELETE FROM Employee WHERE department = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteByDeptSQL)) {

            stmt.setString(1, department);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return new Response(1, rowsAffected); // Return number of deleted records
            } else {
                return new Response(2, "No employees found in department: " + department);
            }

        } catch (SQLException e) {
            return new Response(2, "Database error during department deletion: " + e.getMessage());
        }
    }

    // Delete all employees (use with caution!)
    public Response deleteAllEmployees() {
        String deleteAllSQL = "DELETE FROM Employee";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteAllSQL)) {

            int rowsAffected = stmt.executeUpdate();
            return new Response(1, rowsAffected); // Return number of deleted records

        } catch (SQLException e) {
            return new Response(2, "Database error during bulk deletion: " + e.getMessage());
        }
    }

    // ==================== UTILITY OPERATIONS ====================

    // Get total employee count
    public Response getEmployeeCount() {
        String countSQL = "SELECT COUNT(*) as total FROM Employee";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(countSQL)) {

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt("total");
                return new Response(1, count);
            }
            return new Response(2, "Error getting employee count");

        } catch (SQLException e) {
            return new Response(2, "Database error during count: " + e.getMessage());
        }
    }

    // Test database connection
    public Response testConnection() {
        try (Connection connection = getConnection()) {
            return new Response(1, 0); // Success - connection works
        } catch (SQLException e) {
            return new Response(2, "Database connection failed: " + e.getMessage());
        }
    }
}
package com.litmus7.empManagement.controller;

import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.service.EmployeeService;

import java.util.List;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    //  CREATE OPERATIONS 

    // Import employees from CSV (existing functionality)
    public List<Response> importEmployeesFromCSV(String filepath) {
        if(filepath != null && filepath.toLowerCase().endsWith(".csv")) {
            return employeeService.processEmployeeCSV(filepath);
        }
        return null;
    }

    // Add single employee manually
    public Response addEmployee(int empId, String firstName, String lastName, String email, 
                               String phone, String department, double salary, String joinDate) {
        return employeeService.addEmployee(empId, firstName, lastName, email, phone, department, salary, joinDate);
    }

    //  READ OPERATIONS 

    // Get employee by ID
    public Response getEmployee(int empId) {
        return employeeService.getEmployee(empId);
    }

    // Get and display employee details
    public void displayEmployeeDetails(int empId) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        if (employee != null) {
            System.out.println("=== Employee Details ===");
            System.out.println("ID: " + employee.getEmpId());
            System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Phone: " + employee.getPhone());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Salary: $" + employee.getSalary());
            System.out.println("Join Date: " + employee.getJoinDate());
            System.out.println("========================");
        } else {
            System.out.println("Employee with ID " + empId + " not found.");
        }
    }

    // Display all employees
    public void displayAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
            return;
        }

        System.out.println("\n=== All Employees ===");
        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-15s %-10s %-12s%n", 
                         "ID", "First Name", "Last Name", "Email", "Phone", "Department", "Salary", "Join Date");
        System.out.println("---------------------------------------------------------------------------------------------------");
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-15s %-10.2f %-12s%n",
                emp.getEmpId(), emp.getFirstName(), emp.getLastName(), 
                emp.getEmail(), emp.getPhone(), emp.getDepartment(), 
                emp.getSalary(), emp.getJoinDate());
        }
        System.out.println("Total Employees: " + employees.size());
    }

    // Display employees by department
    public void displayEmployeesByDepartment(String department) {
        List<Employee> employees = employeeService.getEmployeesByDepartment(department);
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
            return;
        }

        System.out.println("\n=== Employees in " + department + " Department ===");
        System.out.printf("%-5s %-15s %-15s %-25s %-15s %-10s %-12s%n", 
                         "ID", "First Name", "Last Name", "Email", "Phone", "Salary", "Join Date");
        System.out.println("-----------------------------------------------------------------------------------------");
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-15s %-15s %-25s %-15s %-10.2f %-12s%n",
                emp.getEmpId(), emp.getFirstName(), emp.getLastName(), 
                emp.getEmail(), emp.getPhone(), emp.getSalary(), emp.getJoinDate());
        }
        System.out.println("Total Employees in " + department + ": " + employees.size());
    }

    // Get employee count
    public Response getEmployeeCount() {
        return employeeService.getEmployeeCount();
    }

    // ==================== UPDATE OPERATIONS ====================

    // Update complete employee details
    public Response updateEmployee(int empId, String firstName, String lastName, String email, 
                                  String phone, String department, double salary, String joinDate) {
        return employeeService.updateEmployee(empId, firstName, lastName, email, phone, department, salary, joinDate);
    }

    // Update employee salary
    public Response updateEmployeeSalary(int empId, double newSalary) {
        return employeeService.updateEmployeeSalary(empId, newSalary);
    }

    // Update employee department
    public Response updateEmployeeDepartment(int empId, String newDepartment) {
        return employeeService.updateEmployeeDepartment(empId, newDepartment);
    }

    // ==================== DELETE OPERATIONS ====================

    // Delete employee by ID
    public Response deleteEmployee(int empId) {
        return employeeService.deleteEmployee(empId);
    }

    // Delete employees by department
    public Response deleteEmployeesByDepartment(String department) {
        return employeeService.deleteEmployeesByDepartment(department);
    }

    // Delete all employees (use with caution!)
    public Response deleteAllEmployees() {
        return employeeService.deleteAllEmployees();
    }

    // ==================== UTILITY OPERATIONS ====================

    // Test database connection
    public Response testDatabaseConnection() {
        return employeeService.testDatabaseConnection();
    }

    // ==================== DISPLAY HELPER METHODS ====================

    // Display responses (existing functionality)
    public void displayResponses(List<Response> responses) {
        if (responses == null) {
            System.out.println("No responses to display.");
            return;
        }
        
        for (Response response : responses) {
            if (response.getCode() == 1) {
                System.out.println("Success: empid = " + response.getIntValue());
            } else if (response.getCode() == 2) {
                System.out.println("Error: " + response.getStringValue());
            } else {
                System.out.println("Unknown response: " + response);
            }
        }
    }

    // Display single response
    public void displayResponse(Response response) {
        if (response == null) {
            System.out.println("No response to display.");
            return;
        }
        
        if (response.getCode() == 1) {
            System.out.println("Success: Operation completed for ID = " + response.getIntValue());
        } else if (response.getCode() == 2) {
            System.out.println("Error: " + response.getStringValue());
        } else {
            System.out.println("Unknown response: " + response);
        }
    }

    // Display operation result with custom success message
    public void displayOperationResult(Response response, String successMessage) {
        if (response == null) {
            System.out.println("No response to display.");
            return;
        }
        
        if (response.getCode() == 1) {
            System.out.println("Success: " + successMessage + " (ID: " + response.getIntValue() + ")");
        } else if (response.getCode() == 2) {
            System.out.println("Error: " + response.getStringValue());
        } else {
            System.out.println("Unknown response: " + response);
        }
    }
}
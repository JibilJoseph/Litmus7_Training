package com.litmus7.empManagement.service;

import com.litmus7.empManagement.dao.EmployeeDAO;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.utils.CSVReader;
import com.litmus7.empManagement.utils.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    // CREATE OPERATIONS 

    // Process CSV file (existing functionality)
    public List<Response> processEmployeeCSV(String filepath) {
        List<Response> responses = new ArrayList<>();
        
        // Read CSV data
        List<String[]> csvData = CSVReader.readCSV(filepath);
        
        // Add any CSV reading errors to responses
        if (CSVReader.hasErrors()) {
            responses.addAll(CSVReader.getErrors());
        }
        
        // Continue processing even if there were CSV errors
        List<Employee> validEmployees = new ArrayList<>();

        for (String[] values : csvData) {
            Employee employee = validateAndCreateEmployee(values);
            if (employee != null) {
                validEmployees.add(employee);
            } else {
                responses.add(new Response(2, "Invalid data in row"));
            }
        }

        // Insert valid employees and add their responses
        List<Response> insertResponses = employeeDAO.insertEmployees(validEmployees);
        responses.addAll(insertResponses);

        return responses;
    }

    // Add single employee manually
    public Response addEmployee(int empId, String firstName, String lastName, String email, 
                               String phone, String department, double salary, String joinDate) {
        
        // Create string array for validation
        String[] values = {
            String.valueOf(empId), firstName, lastName, email, 
            phone, department, String.valueOf(salary), joinDate
        };
        
        // Validate and create employee
        Employee employee = validateAndCreateEmployee(values);
        if (employee == null) {
            return new Response(2, "Invalid employee data provided");
        }
        
        // Insert employee
        return employeeDAO.insertEmployee(employee);
    }

    // READ OPERATIONS 

    // Get employee by ID
    public Response getEmployee(int empId) {
        // Validate employee ID
        if (empId <= 0) {
            return new Response(2, "Invalid employee ID: " + empId);
        }
        
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee != null) {
            return new Response(1, empId); // Success - employee found
        } else {
            return new Response(2, "Employee with ID " + empId + " not found");
        }
    }

    // Get employee details by ID (returns the actual employee object info)
    public Employee getEmployeeDetails(int empId) {
        if (empId <= 0) {
            return null;
        }
        return employeeDAO.getEmployeeById(empId);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            return new ArrayList<>(); // Return empty list for invalid department
        }
        return employeeDAO.getEmployeesByDepartment(department.trim());
    }

    // Get employee count
    public Response getEmployeeCount() {
        return employeeDAO.getEmployeeCount();
    }

    // UPDATE OPERATIONS 

    // Update complete employee details
    public Response updateEmployee(int empId, String firstName, String lastName, String email, 
                                  String phone, String department, double salary, String joinDate) {
        
        // Create string array for validation
        String[] values = {
            String.valueOf(empId), firstName, lastName, email, 
            phone, department, String.valueOf(salary), joinDate
        };
        
        // Validate and create employee
        Employee employee = validateAndCreateEmployee(values);
        if (employee == null) {
            return new Response(2, "Invalid employee data provided for update");
        }
        
        // Update employee
        return employeeDAO.updateEmployee(employee);
    }

    // Update employee salary
    public Response updateEmployeeSalary(int empId, double newSalary) {
        // Validate employee ID
        if (empId <= 0) {
            return new Response(2, "Invalid employee ID: " + empId);
        }
        
        // Validate salary
        if (newSalary <= 0) {
            return new Response(2, "Salary must be positive");
        }
        
        return employeeDAO.updateEmployeeSalary(empId, newSalary);
    }

    // Update employee department
    public Response updateEmployeeDepartment(int empId, String newDepartment) {
        // Validate employee ID
        if (empId <= 0) {
            return new Response(2, "Invalid employee ID: " + empId);
        }
        
        // Validate department
        if (newDepartment == null || newDepartment.trim().isEmpty()) {
            return new Response(2, "Department cannot be empty");
        }
        
        return employeeDAO.updateEmployeeDepartment(empId, newDepartment.trim());
    }

    // DELETE OPERATIONS 

    // Delete employee by ID
    public Response deleteEmployee(int empId) {
        // Validate employee ID
        if (empId <= 0) {
            return new Response(2, "Invalid employee ID: " + empId);
        }
        
        return employeeDAO.deleteEmployee(empId);
    }

    // Delete employees by department
    public Response deleteEmployeesByDepartment(String department) {
        // Validate department
        if (department == null || department.trim().isEmpty()) {
            return new Response(2, "Department cannot be empty");
        }
        
        return employeeDAO.deleteEmployeesByDepartment(department.trim());
    }

    // Delete all employees (use with caution!)
    public Response deleteAllEmployees() {
        return employeeDAO.deleteAllEmployees();
    }

    // UTILITY OPERATIONS 

    // Test database connection
    public Response testDatabaseConnection() {
        return employeeDAO.testConnection();
    }

    // PRIVATE HELPER METHODS 

    private Employee validateAndCreateEmployee(String[] values) {
        try {
            // Validate emp_id
            Response empidResp = Validator.validateEmpid(values[0]);
            if (empidResp.getCode() == 2) return null;
            int empId = empidResp.getIntValue();

            // Validate first name
            Response firstNameResp = Validator.validateFirstName(values[1]);
            if (firstNameResp.getCode() == 2) return null;
            String firstName = values[1].trim();

            // Validate last name
            Response lastNameResp = Validator.validateLastName(values[2]);
            if (lastNameResp.getCode() == 2) return null;
            String lastName = values[2].trim();

            // Validate email
            Response emailResp = Validator.validateEmail(values[3]);
            if (emailResp.getCode() == 2) return null;
            String email = values[3].trim();

            // Validate phone
            Response phoneResp = Validator.validatePhone(values[4]);
            if (phoneResp.getCode() == 2) return null;
            String phone = values[4].trim();

            // Validate department
            Response deptResp = Validator.validateDepartment(values[5]);
            if (deptResp.getCode() == 2) return null;
            String department = values[5].trim();

            // Validate salary
            Response salaryResp = Validator.validateSalary(values[6]);
            if (salaryResp.getCode() == 2) return null;
            double salary = Double.parseDouble(values[6].trim());

            // Validate join date
            Response joinDateResp = Validator.validateJoinDate(values[7]);
            if (joinDateResp.getCode() == 2) return null;
            Date joinDate = Date.valueOf(values[7].trim());

            return new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate);

        } catch (Exception e) {
            return null;
        }
    }
}
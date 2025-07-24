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
    	
    	// creates a DAO when empservice is called
    	
        this.employeeDAO = new EmployeeDAO();
    }
    

    // function to process the csv file
    
    public List<Response> processEmployeeCSV(String filepath) {
    	
    	//reading csv 
        List<String[]> csvData = CSVReader.readCSV(filepath);
        List<Employee> validEmployees = new ArrayList<>();
        List<Response> responses = new ArrayList<>();

        
        // loop to insert valid emp to an array ( validEmployees )
        
        for (String[] values : csvData) {
        	
        	// validating
        	
            Employee employee = validateAndCreateEmployee(values);
            if (employee != null) {
                validEmployees.add(employee);
            } else {
                // Add validation error response
                responses.add(new Response(2, "Invalid data in row"));
            }
        }

        // Insert valid employees and add their response is returned to UI (using DAO)
        
        List<Response> insertResponses = employeeDAO.insertEmployees(validEmployees);
        responses.addAll(insertResponses);

        return responses;
    }

    
    // creating employee object 
    
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

            // if valid , then return employee object 
            
            return new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate);

        } catch (Exception e) {
            System.out.println("Error creating employee: " + e.getMessage());
            return null;
        }
    }
}
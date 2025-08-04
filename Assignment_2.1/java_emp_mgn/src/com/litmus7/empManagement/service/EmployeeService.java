package com.litmus7.empManagement.service;

import com.litmus7.empManagement.dao.EmployeeDAO;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.utils.CSVReader;
import com.litmus7.empManagement.utils.Validator;
import com.litmus7.empManagement.utils.AppLogger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;
    private static final Logger logger = AppLogger.getLogger();

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    // Service 1: Import employees from CSV file to database
    
    public int[] importEmployeesToDB(String filePath) throws IOException 
    {
        List<String[]> EmployeeData;
        int successCount = 0;
        List<String> errorMessages = new ArrayList<>();

        logger.info("Starting employee import process from file: " + filePath);
        
        // Calling Utility Function : CSVReader
        
        EmployeeData = CSVReader.readCSV(filePath);

        if (EmployeeData == null) {
            logger.severe("No valid data found in CSV file");
            return new int[]{0, 0};
        }

        logger.info("Processing " + EmployeeData.size() + " employee records");

        for (int i = 0; i < EmployeeData.size(); i++) {
            String[] row = EmployeeData.get(i);

            if (row.length != 8) {
                String errorMsg = "Row " + (i + 1) + ": Invalid column count";
                logger.warning(errorMsg);
                errorMessages.add(errorMsg);
                continue;
            }

            try {
                int employeeId = Integer.parseInt(row[0].trim());
                String firstName = row[1].trim();
                String lastName = row[2].trim();
                String email = row[3].trim();
                String phone = row[4].trim();
                String department = row[5].trim();
                double salary = Double.parseDouble(row[6].trim());
                LocalDate joinDate = LocalDate.parse(row[7].trim());

                Employee employee = new Employee(employeeId, firstName, lastName, email, phone, department, salary, joinDate);

                
                if (employeeDAO.employeeExists(employeeId)) {
                    String errorMsg = "Row " + (i + 1) + ": Duplicate entry for Employee ID " + employeeId;
                    logger.warning(errorMsg);
                    errorMessages.add(errorMsg);
                    continue;
                }
                if (!Validator.validateEmployee(employee)) {
                    String errorMsg = "Row " + (i + 1) + ": Validation failed";
                    logger.warning(errorMsg);
                    errorMessages.add(errorMsg);
                    continue;
                }
                // If all correct then save the employee to DB
                
                if (employeeDAO.saveEmployee(employee)) {
                    successCount++;
                    logger.fine("Successfully saved employee ID: " + employeeId);
                } else {
                    String errorMsg = "Row " + (i + 1) + ": Failed to save Employee ID " + employeeId + " to DB";
                    logger.warning(errorMsg);
                    errorMessages.add(errorMsg);
                }

            } catch (Exception e) {
                String errorMsg = "Row " + (i + 1) + ": Error - " + e.getMessage();
                logger.severe(errorMsg);
                errorMessages.add(errorMsg);
            }
        }

        logger.info("Import process completed. Total processed: " + EmployeeData.size() + ", Successfully imported: " + successCount + ", Errors: " + errorMessages.size());

        return new int[]{EmployeeData.size() - 1, successCount}; 
    }

    // Service 2: Get all employees
    
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees from database");
        List<Employee> employees = employeeDAO.getAllEmployees();
        
        if (employees != null) {
            logger.info("Successfully retrieved " + employees.size() + " employees");
        } else {
            logger.warning("Failed to retrieve employees from database");
        }
        
        return employees;
    }
}

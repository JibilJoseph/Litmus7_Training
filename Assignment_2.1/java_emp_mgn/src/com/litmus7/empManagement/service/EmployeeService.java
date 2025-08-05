package com.litmus7.empManagement.service;

import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.dao.EmployeeDAO;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.utils.CSVReader;
import com.litmus7.empManagement.utils.Validator;
import com.litmus7.empManagement.utils.AppLogger;
import com.litmus7.empManagement.exception.EmployeeManagementException;

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
    
    public int[] importEmployeesToDB(String filePath) throws EmployeeManagementException 
    {
        List<String[]> EmployeeData;
        int successCount = 0;
        List<String> errorMessages = new ArrayList<>();

        logger.info("Starting employee import process from file: " + filePath);
        
        try {
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
                    
                    employeeDAO.saveEmployee(employee);
                    successCount++;
                    logger.fine("Successfully saved employee ID: " + employeeId);

                } 
                catch (NumberFormatException e) {
                    String errorMsg = "Row " + (i + 1) + ": Invalid number format - " + e.getMessage();
                    logger.warning(errorMsg);
                    errorMessages.add(errorMsg);
                } catch (Exception e) {
                    String errorMsg = "Row " + (i + 1) + ": Error - " + e.getMessage();
                    logger.severe(errorMsg);
                    errorMessages.add(errorMsg);
                }
            }

            logger.info("Import process completed. Total processed: " + EmployeeData.size() + ", Successfully imported: " + successCount + ", Errors: " + errorMessages.size());

            return new int[]{EmployeeData.size() - 1, successCount};
            
        } 
        // Catching Error in CSVReader Function
        catch (EmployeeManagementException e) {
            logger.severe("Employee management exception during import: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            String errorMsg = "Unexpected error during employee import: " + e.getMessage();
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.FAILURE);
        }
    }

    // Service 2: Get all employees
    
    public List<Employee> getAllEmployees() throws EmployeeManagementException {
        logger.info("Fetching all employees from database");
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            logger.info("Successfully retrieved " + employees.size() + " employees");
            return employees;
        }
        catch (Exception e) {
            String errorMsg = "Unexpected error while fetching employees: " + e.getMessage();
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.FAILURE);
        }
    }
}

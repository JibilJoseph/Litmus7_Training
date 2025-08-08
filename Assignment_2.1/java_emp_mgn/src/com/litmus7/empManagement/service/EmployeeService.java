package com.litmus7.empManagement.service;

import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.dao.EmployeeDAO;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.utils.CSVReader;
import com.litmus7.empManagement.utils.Validator;
import com.litmus7.empManagement.utils.AppLogger;
import com.litmus7.empManagement.exception.EmployeeManagementException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;

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
                logger.error("No valid data found in CSV file");
                return new int[]{0, 0};
            }

            logger.info("Processing " + EmployeeData.size() + " employee records");

            for (int i = 0; i < EmployeeData.size(); i++) {
                String[] row = EmployeeData.get(i);

                if (row.length != 8) {
                    String errorMsg = "Row " + (i + 1) + ": Invalid column count";
                    logger.warn(errorMsg);
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
                        logger.warn(errorMsg);
                        errorMessages.add(errorMsg);
                        continue;
                    }
                    if (!Validator.validateEmployee(employee)) {
                        String errorMsg = "Row " + (i + 1) + ": Validation failed";
                        logger.warn(errorMsg);
                        errorMessages.add(errorMsg);
                        continue;
                    }
                    // If all correct then save the employee to DB
                    
                    employeeDAO.saveEmployee(employee);
                    successCount++;
                    logger.debug("Successfully saved employee ID: " + employeeId);

                } catch (NumberFormatException e) {
                    String errorMsg = "Row " + (i + 1) + ": Invalid number format - " + e.getMessage();
                    logger.warn(errorMsg);
                    throw new EmployeeManagementException(errorMsg, e, StatusCodes.INVALID_FORMAT);
                }
            }

            logger.info("Import process completed. Total processed: " + EmployeeData.size() + ", Successfully imported: " + successCount + ", Errors: " + errorMessages.size());

            return new int[]{EmployeeData.size() - 1, successCount};
            
        } catch (EmployeeManagementException e) {
            logger.error("Employee management exception during import: " + e.getMessage());
            throw e;
        }
    }

    // Service 2: Get all employees
    
    public List<Employee> getAllEmployees() throws EmployeeManagementException {
        logger.info("Fetching all employees from database");
        List<Employee> employees = employeeDAO.getAllEmployees();
        logger.info("Successfully retrieved " + employees.size() + " employees");
        return employees;
    }
    
    
 // Service 3: Get employee by ID
    public Employee getEmployeeById(int empId) throws EmployeeManagementException {
        logger.info("Fetching employee with ID: " + empId);
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee != null) {
            logger.info("Successfully retrieved employee with ID: " + empId);
        } else {
            logger.warn("No employee found with ID: " + empId);
        }
        return employee;
    }
 // Service 4: Delete employee by ID
    
    public boolean deleteEmployeeById(int employeeId) throws EmployeeManagementException
    {
    	logger.info("Deleting employee with ID : "+employeeId);
    	boolean deleted=employeeDAO.deleteEmployeeById(employeeId);
    	
    	if (deleted) {
            logger.info("Successfully deleted employee with ID: " + employeeId);
        } else {
            logger.warn("Failed to delete employee with ID: " + employeeId);
        }
    	
    	return deleted;
    }
 // Service 5: Add employee
    
    public boolean addEmployee(Employee employee) throws EmployeeManagementException
    {
    	logger.info("Adding new employee with ID: " + employee.getEmpId());
        if (!Validator.validateEmployee(employee)) {
            throw new EmployeeManagementException("Invalid employee data", StatusCodes.VALIDATION_ERROR);
        }
        if (employeeDAO.employeeExists(employee.getEmpId())) {
            throw new EmployeeManagementException("Employee with ID " + employee.getEmpId() + " already exists.", StatusCodes.DUPLICATE_ENTRY);
        }
        
        boolean added=employeeDAO.saveEmployee(employee);
        if (added) {
            logger.info("Successfully added employee with ID: " + employee.getEmpId());
        } else {
            logger.warn("Failed to add employee with ID: " + employee.getEmpId());
        }
        return added;
    }
  // Service 6 : update Employee
    
    public boolean updateEmployee(Employee employee) throws EmployeeManagementException
    {
    	logger.info("Updating employee with ID: " + employee.getEmpId());
    	if(!Validator.validateEmployee(employee))
    	{
    		throw new EmployeeManagementException(	"invalid Employee",StatusCodes.VALIDATION_ERROR);
    	}
    	if (!employeeDAO.employeeExists(employee.getEmpId())) {
            throw new EmployeeManagementException("Employee with ID " + employee.getEmpId() + " does not exist.", StatusCodes.FILE_NOT_FOUND);
        }
    	
    	boolean updated=employeeDAO.updateEmployee(employee);
    	if (updated) {
            logger.info("Successfully updated employee with ID: " + employee.getEmpId());
        } else {
            logger.warn("Failed to update employee with ID: " + employee.getEmpId());
        }
        return updated;
    	
    	
    }
    
    // Service 7: Add employees in batch
    
    public int[] addEmployeesInBatch(List<Employee> employeeList) throws EmployeeManagementException
    {
    	logger.info("Adding " + employeeList.size() + " employees in batch.");
    	
    	List<Employee> validEmployees=new ArrayList<>();
    	for(Employee employee : employeeList)
    	{
    		if(Validator.validateEmployee(employee) && !employeeDAO.employeeExists(employee.getEmpId()))
    		{
    			validEmployees.add(employee);
    		}
    		else {
                logger.warn("Invalid or duplicate employee skipped: ID " + employee.getEmpId());
            }
    	}
    	
    	return employeeDAO.addEmployeesInBatch(validEmployees);
    }
    
    // Service 7 : Transfer Employees to Department
    
    public boolean transferEmployeesToDepartment(List<Integer> employeeIds,String newDepartment) throws EmployeeManagementException
    {
        logger.info("Transferring " + employeeIds.size() + " employees to department " + newDepartment);
        return employeeDAO.transferEmployeesToDepartment(employeeIds, newDepartment);
    }
    
}

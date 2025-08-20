package com.litmus7.empManagement.controller;

import java.util.List;

import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.service.EmployeeService;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.constants.MessageConstants;
import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.exception.EmployeeManagementException;
import com.litmus7.empManagement.utils.AppLogger;
import com.litmus7.empManagement.utils.ErrorCodeManager;

import org.apache.logging.log4j.Logger;

public class EmployeeController {
	
	
    private EmployeeService employeeService;
    
    private static final Logger logger = AppLogger.getLogger();

    public EmployeeController() {
        this.employeeService = new EmployeeService();
     
    }
    
    // controller function 1 : Write to DataBase
    
    public Response<Integer> writeDataToDB(String filepath)
    {
    	logger.info("Starting database write operation for file: " + filepath);
    	
    	if (filepath == null || filepath.trim().isEmpty()) 
    	{
    		logger.warn("Missing file path provided");
    		return new Response<>(StatusCodes.FAILURE,MessageConstants.MISSING_FILE_PATH,null);
    	}
    	
    	if (!filepath.toLowerCase().endsWith(".csv")) {
    		logger.warn("Invalid file format provided: " + filepath + " (expected .csv)");
	        return new Response<>(StatusCodes.FAILURE, MessageConstants.INVALID_FILE_FORMAT, null);
	    }
    	
    	try {
    		// Calling Service which return (total,inserted count )
    		int[] result = employeeService.importEmployeesToDB(filepath);
    		
    		int total = result[0];
    	    int insertedCount = result[1];
    	    
    	    logger.info("Database write operation completed. Total: " + total + ", Inserted: " + insertedCount);
    	    
    	    if (insertedCount == 0) 
    	    {
    	    	logger.warn("No records were inserted into database");
    	    	return new Response<>(500, MessageConstants.NO_RECORDS_INSERTED, null);
    	    } 
    	    else if (insertedCount < total) 
    	    {
    	    	logger.warn("Partial insert completed: " + insertedCount + " of " + total + " records");
    	    	return new Response<Integer>(StatusCodes.PARTIAL_SUCCESS, String.format(MessageConstants.PARTIAL_INSERT, insertedCount, total), insertedCount);
    	    } 
    	    else
    	    {
    	    	logger.info("All records successfully inserted into database");
    	        return new Response<>(StatusCodes.SUCCESS, null, insertedCount); 
    	    }
    	} catch (EmployeeManagementException e) {
    		logger.error("Employee management exception in writeDataToDB: " + e.getMessage());
    		return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()), null);
    	} catch (Exception e) {
    		logger.error("Unexpected error in writeDataToDB: " + e.getMessage());
    		return new Response<>(701, ErrorCodeManager.getMessage(701), null);
    	}
    }

 // controller function 2 : Get All Employee Details
    
    public Response<List<Employee>> getAllEmployees() {
		logger.info("Starting to fetch all employee details");
		
		try {
			List<Employee> employees = employeeService.getAllEmployees();
			logger.info("Successfully fetched " + employees.size() + " employee records");
			return new Response<>(StatusCodes.SUCCESS,MessageConstants.EMPLOYEE_DATA_FETCH_SUCCESS,employees);
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in getAllEmployees: " + e.getMessage());
			return new Response<>(e.getStatusCode(),ErrorCodeManager.getMessage(e.getStatusCode()));
		} catch (Exception e) {
			logger.error("Unexpected error in getAllEmployees: " + e.getMessage());
			return new Response<>(701,ErrorCodeManager.getMessage(701));
		}
	}
    
    // controller function 3 : Get Employee by ID
    
    public Response<Employee> getEmployeeById(int employeeId)
    {
    	try {
			Employee employee=employeeService.getEmployeeById(employeeId);
			if (employee != null) {
				logger.info("Successfully fetched employee record for ID: " + employeeId);
				return new Response<>(StatusCodes.SUCCESS, "Employee found", employee);
			} else {
				logger.warn("No employee found with ID: " + employeeId);
				return new Response<>(StatusCodes.FILE_NOT_FOUND, "Employee not found", null);
			}
		}  catch (EmployeeManagementException e) {
			logger.error("Employee management exception in getEmployeeById: " + e.getMessage());
			return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()));
		} catch (Exception e) {
			logger.error("Unexpected error in getEmployeeById: " + e.getMessage());
			return new Response<>(701, ErrorCodeManager.getMessage(701));
		}
    	
    }
    
    // controller function 4 : Delete Employee by ID
    
    public Response<Boolean> deleteEmployeeById(int employeeId)
    {
    	try {
			boolean deleted=employeeService.deleteEmployeeById(employeeId);
			if (deleted) {
				logger.info("Successfully deleted employee with ID: " + employeeId);
				return new Response<>(StatusCodes.SUCCESS, MessageConstants.DELETE_SUCCESS, true);
			} else {
				logger.warn("Failed to delete employee with ID: " + employeeId);
				return new Response<>(StatusCodes.FAILURE,MessageConstants.DELETE_FAILED, false);
			}
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in deleteEmployeeById: " + e.getMessage());
			return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()), false);
		} catch (Exception e) {
			logger.error("Unexpected error in deleteEmployeeById: " + e.getMessage());
			return new Response<>(701,ErrorCodeManager.getMessage(701), false);
		}
    	
    }
    
    // controller function 5 : Add Employee
    
    public Response<Boolean> addEmployee(Employee employee) 
    {
    	logger.info("Starting to add new employee with ID: " + employee.getEmpId());
    	
    	
    	try {
			boolean added = employeeService.addEmployee(employee);
			if (added) {
				logger.info("Successfully added employee with ID: " + employee.getEmpId());
				return new Response<>(StatusCodes.SUCCESS, "Employee added successfully", true);
			} else {
				logger.warn("Failed to add employee with ID: " + employee.getEmpId());
				return new Response<>(StatusCodes.FAILURE, "Failed to add employee", false);
			}
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in addEmployee: " + e.getMessage());
			return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()), false);
		} catch (Exception e) {
			logger.error("Unexpected error in addEmployee: " + e.getMessage());
			return new Response<>(701, ErrorCodeManager.getMessage(701), false);
		}
    	
    }
    
    // controller function 6 : Update Employee
    
    public Response<Boolean> updateEmployee(Employee employee)
    {
    	try {
			boolean updated=employeeService.updateEmployee(employee);
			if (updated) {
				logger.info("Successfully updated employee with ID: " + employee.getEmpId());
				return new Response<>(StatusCodes.SUCCESS, "Employee updated successfully", true);
			} else {
				logger.warn("Failed to update employee with ID: " + employee.getEmpId());
				return new Response<>(StatusCodes.FAILURE, "Failed to update employee", false);
			}
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in updateEmployee: " + e.getMessage());
			return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()), false);
		} catch (Exception e) {
			logger.error("Unexpected error in updateEmployee: " + e.getMessage());
			return new Response<>(701, ErrorCodeManager.getMessage(701), false);
		}
    	
    }
    
    // controller function 7 : Add Employees in Batch
    
    public Response<int[]> addEmployeesInBatch(List<Employee> employeeList)
    {
    	logger.info("Starting to add " + employeeList.size() + " employees in batch.");
    	
    	try {
    		
			int[] result=employeeService.addEmployeesInBatch(employeeList);
			int successCount=0;
			for(int i:result)
			{
				if(i>=0)
				{
					successCount++;
				}
			}
			
			// Checking sucessCount
			
			if (successCount == employeeList.size()) {
				logger.info("All employees added successfully in batch.");
				return new Response<>(200, "All employees added successfully.", result);
			} else {
				logger.warn("Partial success in batch employee addition. " + successCount + " out of "
						+ employeeList.size() + " added.");
				return new Response<>(206,
						"Partially added employees. " + successCount + " out of " + employeeList.size() + " succeeded.",
						result);
			}
			
			
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in addEmployeesInBatch: " + e.getMessage());
			return new Response<>(e.getStatusCode(), ErrorCodeManager.getMessage(e.getStatusCode()), null);
		} catch (Exception e) {
			logger.error("Unexpected error in addEmployeesInBatch: " + e.getMessage());
			return new Response<>(701, ErrorCodeManager.getMessage(701),
					null);
		}
    			
    	
    }
    
    // controller function 8 : transfer Employees to Department
    
    public Response<Boolean> transferEmployeesToDepartment(List<Integer> employeeIds,String newDepartment)
    {
    	logger.info("Starting to transfer " + employeeIds.size() + " employees to department " + newDepartment);
    	try {
			boolean sucess=employeeService.transferEmployeesToDepartment(employeeIds, newDepartment);
			
			if(sucess)
			{
				logger.info("Successfully transferred employees.");
				return new Response<>(200,"Employees transfered successfully");
			}
			else
			{
				logger.warn("Failed to transfer employees.");
	            return new Response<>(500, "Failed to transfer employees.");
			}
		} catch (EmployeeManagementException e) {
			logger.error("Employee management exception in transferEmployeesToDepartment: " + e.getMessage());
			return new Response<>(e.getStatusCode(),ErrorCodeManager.getMessage(e.getStatusCode()));
		}
    	catch(Exception e)
    	{
    		logger.error("Unexpected error in transferEmployeesToDepartment: " + e.getMessage());
            return new Response<>(701, ErrorCodeManager.getMessage(701));
    	}
		
    	
    }
}
    
    
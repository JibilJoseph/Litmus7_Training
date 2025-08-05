package com.litmus7.empManagement.controller;

import java.io.IOException;
import java.util.List;

import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.service.EmployeeService;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.exception.EmployeeManagementException;
import com.litmus7.empManagement.utils.AppLogger;


import java.util.logging.Logger;

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
    		logger.warning("Missing file path provided");
    		return new Response<>(StatusCodes.FAILURE,"Missing File Path",null);
    	}
    	
    	if (!filepath.toLowerCase().endsWith(".csv")) {
    		logger.warning("Invalid file format provided: " + filepath + " (expected .csv)");
	        return new Response<>(StatusCodes.FAILURE, "Given File is Not CSV", null);
	    }
    	
    	try {
    		// Calling Service which return (total,inserted count )
    		int[] result = employeeService.importEmployeesToDB(filepath);
    		
    		int total = result[0];
    	    int insertedCount = result[1];
    	    
    	    logger.info("Database write operation completed. Total: " + total + ", Inserted: " + insertedCount);
    	    
    	    if (insertedCount == 0) 
    	    {
    	    	logger.warning("No records were inserted into database");
    	        return new Response<>(500, "No records were inserted.", null);
    	    } 
    	    else if (insertedCount < total) 
    	    {
    	    	logger.warning("Partial insert completed: " + insertedCount + " of " + total + " records");
    	        return new Response<>(StatusCodes.PARTIAL_SUCCESS, "Partial insert: " + insertedCount + " of " + total + " inserted.", insertedCount);
    	    } 
    	    else
    	    {
    	    	logger.info("All records successfully inserted into database");
    	        return new Response<>(StatusCodes.SUCCESS, null, insertedCount); 
    	    }
    	} catch (EmployeeManagementException e) {
    		logger.severe("Employee management exception in writeDataToDB: " + e.getMessage());
    		return new Response<>(e.getStatusCode(), "Error: " + e.getMessage(), null);
    	} catch (Exception e) {
    		logger.severe("Unexpected error in writeDataToDB: " + e.getMessage());
    		return new Response<>(StatusCodes.FAILURE, "Unexpected error occurred", null);
    	}
    }

 // controller function 2 : Get All Employee Details
    
    public Response<List<Employee>> getAllEmployees() {
		logger.info("Starting to fetch all employee details");
		
		try {
			List<Employee> employees = employeeService.getAllEmployees();
			logger.info("Successfully fetched " + employees.size() + " employee records");
			return new Response<>(StatusCodes.SUCCESS,"Employee Data Fetch Success",employees);
		} catch (EmployeeManagementException e) {
			logger.severe("Employee management exception in getAllEmployees: " + e.getMessage());
			return new Response<>(e.getStatusCode(),"Failed to Fetch Employee Data: " + e.getMessage());
		} catch (Exception e) {
			logger.severe("Unexpected error in getAllEmployees: " + e.getMessage());
			return new Response<>(StatusCodes.FAILURE,"Unexpected error occurred while fetching employee data");
		}
	}
}
    
    
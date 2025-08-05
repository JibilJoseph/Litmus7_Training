package com.litmus7.empManagement.dao;

import com.litmus7.empManagement.model.Employee;

import com.litmus7.empManagement.utils.DatabaseConfig;
import com.litmus7.empManagement.constants.SQLConstants;
import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.utils.AppLogger;
import com.litmus7.empManagement.exception.EmployeeManagementException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmployeeDAO {

	private static final Logger logger = AppLogger.getLogger();
	
	 // DB Function : Save Employee
	 public boolean saveEmployee(Employee employee) throws SQLIntegrityConstraintViolationException, EmployeeManagementException {
		 
	    	try (Connection conn = DatabaseConfig.getConnection();
	    	        PreparedStatement stmt = conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE)) 
	    	{

	    	        stmt.setInt(1, employee.getEmpId());
	    	        stmt.setString(2, employee.getFirstName());
	    	        stmt.setString(3, employee.getLastName());
	    	        stmt.setString(4, employee.getEmail());
	    	        stmt.setString(5, employee.getPhone());
	    	        stmt.setString(6, employee.getDepartment());
	    	        stmt.setDouble(7, employee.getSalary());
	    	        stmt.setDate(8, java.sql.Date.valueOf(employee.getJoinDate()));

	    	        int rowsInserted = stmt.executeUpdate();
	    	        boolean success = rowsInserted > 0;
	    	        if (success) {
	    	            logger.fine("Successfully saved employee ID: " + employee.getEmpId());
	    	        } else {
	    	            logger.warning("Failed to save employee ID: " + employee.getEmpId());
	    	        }
	    	        return success;

	    	}
	     	catch (SQLIntegrityConstraintViolationException e) 
	    	{
		       String errorMsg = "Duplicate employee ID: " + employee.getEmpId();
		       logger.severe(errorMsg);
		       throw new EmployeeManagementException(errorMsg, e, StatusCodes.DUPLICATE_ENTRY);
	    	}
	    	catch (SQLException e) {
		       String errorMsg = "Database error while saving employee ID " + employee.getEmpId() + ": " + e.getMessage();
		       logger.severe(errorMsg);
		       throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
	    	}
}
	    
	 
	 	// Get All Employees
	    
	    public List<Employee> getAllEmployees() 
	    {
	        List<Employee> employees = new ArrayList<>();

	        try (Connection conn = DatabaseConfig.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(SQLConstants.SELECT_ALL_EMPLOYEES);
	             ResultSet rs = stmt.executeQuery()) 
	        {

	            while (rs.next()) {
	                Employee emp = new Employee();
	                emp.setEmpId(rs.getInt("emp_id"));
	                emp.setFirstName(rs.getString("first_name"));
	                emp.setLastName(rs.getString("last_name"));
	                emp.setEmail(rs.getString("email"));
	                emp.setPhone(rs.getString("phone"));
	                emp.setDepartment(rs.getString("department"));
	                emp.setSalary(rs.getDouble("salary"));
	                emp.setJoinDate(rs.getDate("join_date").toLocalDate());

	                employees.add(emp);
	            }
	            
	            logger.info("Successfully retrieved " + employees.size() + " employees from database");
	            
	        } catch (SQLException e) {
	            logger.severe("Database error while retrieving employees: " + e.getMessage());
	        }

	        return employees;
	    }
	    
	    public boolean employeeExists(int empId) throws EmployeeManagementException {
	        try (Connection conn = DatabaseConfig.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(SQLConstants.CHECK_EMPLOYEE_EXISTS)) 
	        {
	            
	            stmt.setInt(1, empId);
	            ResultSet rs = stmt.executeQuery();
	            boolean exists = rs.next();
	            
	            if (exists) {
	                logger.fine("Employee ID " + empId + " already exists in database");
	            }
	            
	            return exists;
	        } 
	        catch (SQLException e) 
			{
	            String errorMsg = "Database error while retrieving employees: " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
	        }
	    }
}

package com.litmus7.empManagement.dao;

import com.litmus7.empManagement.model.Employee;

import com.litmus7.empManagement.utils.DatabaseConfig;
import com.litmus7.empManagement.constants.SQLConstants;
import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.utils.AppLogger;
import com.litmus7.empManagement.exception.EmployeeManagementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmployeeDAO {

	private static final Logger logger = AppLogger.getLogger();
	
	 // DB Function : Save Employee
	 public boolean saveEmployee(Employee employee) throws EmployeeManagementException {
		 
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
    	 catch (SQLException e) {
    	        String errorMsg = "Database error while saving employee ID " + employee.getEmpId() + ": " + e.getMessage();
    	        logger.severe(errorMsg);
    	        throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
    	    }
	    }
	 
	 	// Get All Employees
	    
	    public List<Employee> getAllEmployees() throws EmployeeManagementException 
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
	            String errorMsg = "Database error while retrieving employees: " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
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
	                } catch (SQLException e) {
            String errorMsg = "Database error while checking employee existence for ID " + empId + ": " + e.getMessage();
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
        }
	    }
	    
	    public Employee getEmployeeById(int employeeId) throws EmployeeManagementException
	    {
	    	Employee employee=null;
	    	try(Connection conn=DatabaseConfig.getConnection();
	    		PreparedStatement stmt=conn.prepareStatement(SQLConstants.SELECT_EMPLOYEE_BY_ID))
	    	{
	    		stmt.setInt(1, employeeId);
	    		ResultSet rs=stmt.executeQuery();
	    		if(rs.next())
	    		{
	    			employee=new Employee();
	    			employee.setEmpId(rs.getInt("emp_id"));
	    			employee.setFirstName(rs.getString("first_name"));
	    			employee.setLastName(rs.getString("last_name"));
	                employee.setEmail(rs.getString("email"));
	                employee.setPhone(rs.getString("phone"));
	                employee.setDepartment(rs.getString("department"));
	                employee.setSalary(rs.getDouble("salary"));
	                employee.setJoinDate(rs.getDate("join_date").toLocalDate());
	    		}
	    		
	    		
	    	}
	    	catch (SQLException e) {
	            String errorMsg = "Database error while retrieving employee with ID " + employeeId + ": " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
	        }
	        
	    	return employee;
	    }
	    
	    public boolean deleteEmployeeById(int employeeId) throws EmployeeManagementException
	    {
	    	try(Connection conn=DatabaseConfig.getConnection();
	    		PreparedStatement stmt=conn.prepareStatement(SQLConstants.DELETE_EMPLOYEE_BY_ID))
	    	{
	    		stmt.setInt(1, employeeId);
	    		int rowsAffected=stmt.executeUpdate();
	    		return rowsAffected>0;
	    	}
	    	catch(SQLException e)
	    	{
	    		String errorMsg = "Database error while deleting employee with ID " + employeeId + ": " + e.getMessage();
	    		logger.severe(errorMsg);
	    		throw new EmployeeManagementException(errorMsg,e,StatusCodes.DATABASE_ERROR);
	    	}
	    	
	    }
	    
	    // update Employee
	    
	    public boolean updateEmployee(Employee employee) throws EmployeeManagementException
	    {
	    	try(Connection conn=DatabaseConfig.getConnection();
	    		PreparedStatement stmt=conn.prepareStatement(SQLConstants.UPDATE_EMPLOYEE))
	    	{
	    		stmt.setString(1, employee.getFirstName());
	    		stmt.setString(2, employee.getLastName());
	            stmt.setString(3, employee.getEmail());
	            stmt.setString(4, employee.getPhone());
	            stmt.setString(5, employee.getDepartment());
	            stmt.setDouble(6, employee.getSalary());
	            stmt.setDate(7, java.sql.Date.valueOf(employee.getJoinDate()));
	            stmt.setInt(8, employee.getEmpId());
	            
	            int rowsAffected=stmt.executeUpdate();
	            return rowsAffected>0;
	    	}
	    	catch (SQLException e) {
	            String errorMsg = "Database error while updating employee with ID " + employee.getEmpId() + ": " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
	        }
	    	
	    	
	    }
	    
	    // Add Employees in Batch
	    public int[] addEmployeesInBatch(List<Employee> employeeList) throws EmployeeManagementException
	    {
	    	try(Connection conn=DatabaseConfig.getConnection();
	    		PreparedStatement stmt=conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE))
	    	{
	    		for(Employee employee : employeeList)
	    		{
	    			stmt.setInt(1, employee.getEmpId());
	    			stmt.setString(2, employee.getFirstName());
	                stmt.setString(3, employee.getLastName());
	                stmt.setString(4, employee.getEmail());
	                stmt.setString(5, employee.getPhone());
	                stmt.setString(6, employee.getDepartment());
	                stmt.setDouble(7, employee.getSalary());
	                stmt.setDate(8, java.sql.Date.valueOf(employee.getJoinDate()));
	                
	                stmt.addBatch();
	    		}
	    		
	    		int[] updateCounts=stmt.executeBatch();
	    		int successCount=0;
	    		for(int count : updateCounts)
	    		{
	    			if(count>=0)
	    					successCount++;
	    		}
	    		
	    		//adding to logger
	    		logger.info("Successfully inserted " + successCount + " out of " + employeeList.size() + " employees in batch.");
	            return updateCounts;
	    	}
	    	catch (SQLException e) {
	            String errorMsg = "Database error during batch employee insert: " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);
	        }
	    }
	    
	    // Transfer Employees To Department
	    
	    public boolean transferEmployeesToDepartment(List<Integer> employeeIds, String newDepartment) throws EmployeeManagementException 
	    {
	        Connection conn = null;
	        try {
	            conn = DatabaseConfig.getConnection();
	            conn.setAutoCommit(false); 

	            try (PreparedStatement stmt = conn.prepareStatement(SQLConstants.UPDATE_EMPLOYEE_DEPARTMENT)) 
	            {
	                for (Integer empId : employeeIds) 
	                {
	                    stmt.setString(1, newDepartment);
	                    stmt.setInt(2, empId);
	                    stmt.addBatch();
	                }
	                stmt.executeBatch();
	            }

	            conn.commit(); 
	            logger.info("Successfully transferred " + employeeIds.size() + " employees to department " + newDepartment);
	            return true;

	        } 
	        catch (SQLException e) 
	        {
	            if (conn != null) {
	                try {
	                    conn.rollback(); 
	                    logger.warning("Transaction rolled back due to an error: " + e.getMessage());
	                } catch (SQLException ex) {
	                    logger.severe("Failed to rollback transaction: " + ex.getMessage());
	                }
	            }
	            String errorMsg = "Database error during employee transfer: " + e.getMessage();
	            logger.severe(errorMsg);
	            throw new EmployeeManagementException(errorMsg, e, StatusCodes.DATABASE_ERROR);

	        } 
	        finally 
	        {
	            if (conn != null) 
	            {
	                try {
	                    conn.setAutoCommit(true); 
	                    conn.close();
	                } catch (SQLException e) {
	                    logger.severe("Failed to close connection: " + e.getMessage());
	                }
	            }
	        }
	    }
}

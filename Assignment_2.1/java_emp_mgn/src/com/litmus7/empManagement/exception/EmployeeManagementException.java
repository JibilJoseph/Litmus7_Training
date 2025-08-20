package com.litmus7.empManagement.exception;


public class EmployeeManagementException extends Exception {

	private static final long serialVersionUID = 1L;
	private final int statusCode;
    

    // Constructor with message and status code
  
    public EmployeeManagementException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    // Constructor with message, cause and status code
    
    public EmployeeManagementException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
    
    // Get the status code
     
    public int getStatusCode() {
        return statusCode;
    }
}
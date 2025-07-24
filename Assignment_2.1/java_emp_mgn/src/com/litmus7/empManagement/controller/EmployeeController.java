package com.litmus7.empManagement.controller;

import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.service.EmployeeService;

import java.util.List;

public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController() {
    	
    	//controller creates a service instance 
        this.employeeService = new EmployeeService();
    }

    // to import csv check file and then calls processempcsv of Service class
    
    public List<Response> importEmployeesFromCSV(String filepath) {
    	if(filepath != null && filepath.toLowerCase().endsWith(".csv")) {
            return employeeService.processEmployeeCSV(filepath);
        }
		return null;
    }
    
    // to display responses

    public void displayResponses(List<Response> responses) {
        for (Response response : responses) {
            if (response.getCode() == 1) {
                System.out.println("Success: empid = " + response.getIntValue());
            } else if (response.getCode() == 2) {
                System.out.println("Error: " + response.getStringValue());
            } else {
                System.out.println("Unknown response: " + response);
            }
        }
    }
}
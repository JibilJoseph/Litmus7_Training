package com.litmus7.empManagement;

import com.litmus7.empManagement.controller.EmployeeController;
import com.litmus7.empManagement.model.Response;

import java.util.List;

public class Main {
    public static void main(String[] args) {
    	

        String filepath = "employees.csv";
        
        EmployeeController controller = new EmployeeController();
        List<Response> responses = controller.importEmployeesFromCSV(filepath);
        
        
        // display responses to the user
        controller.displayResponses(responses);
    }
}
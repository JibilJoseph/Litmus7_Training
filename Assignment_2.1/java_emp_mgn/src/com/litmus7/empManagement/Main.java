package com.litmus7.empManagement;

import com.litmus7.empManagement.controller.EmployeeController;
import com.litmus7.empManagement.model.Response;

import java.util.List;

public class Main {
    public static void main(String[] args) {
    	

        String filepath = "employees.csv";
        
        EmployeeController controller = new EmployeeController();
        List<Response> responses = controller.importEmployeesFromCSV(filepath);
        
        
        controller.displayResponses(responses);
        
        
        // delete function
        //Response responses1 =controller.deleteEmployee(101);
        
        
        // display responses to the user
        
        //System.out.println(responses1);
    }
}
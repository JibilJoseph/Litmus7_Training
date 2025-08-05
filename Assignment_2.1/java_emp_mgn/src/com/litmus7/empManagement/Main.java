package com.litmus7.empManagement;

import java.util.List;

import com.litmus7.empManagement.controller.EmployeeController;
import com.litmus7.empManagement.model.Employee;
import com.litmus7.empManagement.model.Response;



public class Main {
    public static void main(String[] args)  {
    	

        String filepath = "employees.csv";
        
      //Using WriteToDB Function
        
        EmployeeController controller = new EmployeeController();
        Response<Integer> response=controller.writeDataToDB(filepath);
        
        if(response.getStatusCode()==200)
        {
        	System.out.println("All records Inserted");
        }
        //partial insertion
        else if(response.getStatusCode()==207)
        {
        	System.out.println("Message : "+response.getMessage());
        	System.out.println("Inserted Count: " + response.getData());
        }
        else
        {
        	System.out.println("Message : "+response.getMessage());
        }
        
        //Using GetAllEmployees Function
        
        Response<List<Employee>> employeeListResponse=controller.getAllEmployees();
        if(employeeListResponse.getStatusCode()!=200) 
        {
        	System.out.println("Message: " + employeeListResponse.getMessage() + employeeListResponse.getStatusCode());
        	
        }
        else
        {
        	List<Employee> employeeData=employeeListResponse.getData();
        	for(Employee emp : employeeData)
        	{
        		System.out.println(emp);
        	}
        }
        
        
       
    }
}
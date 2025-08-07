package com.litmus7.empManagement;

import java.time.LocalDate;
import java.util.ArrayList;
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
        
        
        // Calling getEmployeeById
        
        Response<Employee> employeeResponse = controller.getEmployeeById(102);
        if (employeeResponse.getStatusCode() == 200) {
            System.out.println("Employee Found: " + employeeResponse.getData());
        } else {
            System.out.println("Message: " + employeeResponse.getMessage());
        }
        
        // Calling deleteEmployeeById 
        
        Response<Boolean> deleteResponse=controller.deleteEmployeeById(101);
        if (deleteResponse.getStatusCode() == 200) {
            System.out.println("Employee Deleted: " + deleteResponse.getData());
        } else {
            System.out.println("Message: " + deleteResponse.getMessage());
        }
        
        // Calling addEmployee
        
        Employee newEmployee = new Employee(104, "John", "Doe", "john.doe@example.com", "1234567890", "IT", 50000, LocalDate.now());
        Response<Boolean> addEmployeeResponse=controller.addEmployee(newEmployee);
        if (addEmployeeResponse.getStatusCode() == 200) {
            System.out.println("Employee Added: " + addEmployeeResponse.getData());
        } else {
            System.out.println("Message: " + addEmployeeResponse.getMessage());
        }
        
        //Using UpdateEmployee Function
        
        Employee updatedEmployee = new Employee(104, "John", "Doe", "john.doe@example.com", "1234567890", "IT", 60000, LocalDate.now());
        Response<Boolean> updateResponse = controller.updateEmployee(updatedEmployee);
        if (updateResponse.getStatusCode() == 200) {
            System.out.println("Employee Updated: " + updateResponse.getData());
        } else {
            System.out.println("Message: " + updateResponse.getMessage());
        }
        
        
        //Using AddEmployeesInBatch Function
        
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(111, "Jane", "Doe", "jane.doe@example.com", "0987654321", "HR", 45000, LocalDate.now()));
        employeeList.add(new Employee(112, "Peter", "Jones", "peter.jones@example.com", "1122334455", "Finance", 70000, LocalDate.now()));
        
        Response<int[]> batchAddResponse = controller.addEmployeesInBatch(employeeList);
        if (batchAddResponse.getStatusCode() == 200) {
            System.out.println("Batch Add Successful");
        } else {
            System.out.println("Message: " + batchAddResponse.getMessage());
        }
        
        // Using transferEmployeesToDepartment Function
        
        List<Integer> employeeIdsToTransfer = new ArrayList<>();
        employeeIdsToTransfer.add(102);
        employeeIdsToTransfer.add(103);
        String newDepartment = "HR";
        
        Response<Boolean> transferResponse = controller.transferEmployeesToDepartment(employeeIdsToTransfer, newDepartment);
        if (transferResponse.getStatusCode() == 200) {
            System.out.println("Employees Transferred: Sucess ");
        } else {
            System.out.println("Message: " + transferResponse.getMessage());
        }
        
    }
}
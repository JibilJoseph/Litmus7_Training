package com.litmus7.empManagement;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String filepath="employees.csv";
		EmployeeManagerController controller=new EmployeeManagerController();
		
		List<Response> responses = controller.writeDataToDB(filepath);
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

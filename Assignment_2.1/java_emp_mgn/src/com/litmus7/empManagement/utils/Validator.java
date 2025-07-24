package com.litmus7.empManagement.utils;

import com.litmus7.empManagement.model.Response;
import java.sql.Date;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,15}$");

    public static Response validateEmpid(String value) {
        try {
            int empid = Integer.parseInt(value.trim());
            if (empid <= 0) return new Response(2, "Invalid empid");
            return new Response(1, empid);
        } catch (NumberFormatException e) {
            return new Response(2, "empid must be an integer");
        }
    }

    public static Response validateFirstName(String value) {
        String firstName = value.trim();
        if (firstName.isEmpty()) return new Response(2, "First name is empty");
        return new Response(1, 0);
    }

    public static Response validateLastName(String value) {
        String lastName = value.trim();
        if (lastName.isEmpty()) return new Response(2, "Last name is empty");
        return new Response(1, 0);
    }

    public static Response validateEmail(String value) {
        String email = value.trim();
        if (!EMAIL_PATTERN.matcher(email).matches()) 
            return new Response(2, "Invalid email format");
        return new Response(1, 0);
    }

    public static Response validatePhone(String value) {
        String phone = value.trim();
        if (!PHONE_PATTERN.matcher(phone).matches()) 
            return new Response(2, "Invalid phone number");
        return new Response(1, 0);
    }

    public static Response validateDepartment(String value) {
        String department = value.trim();
        if (department.isEmpty()) return new Response(2, "Department is empty");
        return new Response(1, 0);
    }

    public static Response validateSalary(String value) {
        try {
            double salary = Double.parseDouble(value.trim());
            if (salary <= 0) return new Response(2, "Salary must be positive");
            return new Response(1, (int)salary);
        } catch (NumberFormatException e) {
            return new Response(2, "Salary must be a number");
        }
    }

    public static Response validateJoinDate(String value) {
        try {
            Date joinDate = Date.valueOf(value.trim());
            return new Response(1, 0);
        } catch (IllegalArgumentException e) {
            return new Response(2, "Invalid join date format (expected yyyy-[m]m-[d]d)");
        }
    }
}
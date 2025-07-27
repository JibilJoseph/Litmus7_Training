package com.litmus7.empManagement.utils;

import com.litmus7.empManagement.model.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    
    // Store errors at class level
    private static List<Response> errors = new ArrayList<>();
    
    public static List<String[]> readCSV(String filepath) {
        List<String[]> data = new ArrayList<>();
        errors.clear(); // Clear previous errors
        
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;
            
            // Skip header line
            br.readLine();
            lineNumber++;
            
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    errors.add(new Response(2, "Empty line at line " + lineNumber));
                    continue;
                }
                
                String[] values = line.split(",");
                
                if (values.length != 8) {
                    errors.add(new Response(2, "Incomplete row at line " + lineNumber + ": expected 8 columns, got " + values.length));
                    continue;
                }
                
                // FIX: Actually add valid data to list!
                data.add(values);
            }
            
        } catch (Exception e) {
            errors.add(new Response(2, "Error reading CSV file: " + e.getMessage()));
        }
        
        return data;
    }
    
    // Method to get errors
    public static List<Response> getErrors() {
        return new ArrayList<>(errors); // Return copy to prevent modification
    }
    
    // Method to check if there were errors
    public static boolean hasErrors() {
        return !errors.isEmpty();
    }
}
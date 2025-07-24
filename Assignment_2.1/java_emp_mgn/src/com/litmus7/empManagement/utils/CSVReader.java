package com.litmus7.empManagement.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    
    public static List<String[]> readCSV(String filepath) {
        List<String[]> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 8) {
                    System.out.println("Incomplete Row, skipping: " + line);
                    continue;
                }
                data.add(values);
            }
        } catch (Exception e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return data;
    }
}
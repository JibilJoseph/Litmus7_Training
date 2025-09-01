package com.litmus7.inventory.utils;

import java.io.File;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.inventory.models.InventoryRecord;


public class CSVProcessor {

    private static final String INPUT_DIRECTORY = "resources/input";
    private static final String PROCESSED_DIRECTORY = "resources/processed";
    private static final String ERROR_DIRECTORY = "resources/error";

    //Function 1 : Get CSV Files inside the input folder
    public File[] getCSVFiles() 
    {
        File directory = new File(INPUT_DIRECTORY);
        return directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
    }

    // Function 2 : ParseCSV : returns the rows in each file
    public List<InventoryRecord> parseCSV(File csvFile) throws IOException 
    {
        List<InventoryRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
        {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Skip header row if present
                if (lineNumber == 1 && line.toLowerCase().contains("sku")) {
                    continue;
                }
                
                String[] data = line.split(",");
                if (data.length != 4) {
                    throw new IOException("Malformed row at line " + lineNumber + ": " + line);
                }
                try {
                    int sku = Integer.parseInt(data[0].trim());
                    String productName = data[1].trim();
                    int quantity = Integer.parseInt(data[2].trim());
                    double price = Double.parseDouble(data[3].trim());

                    if (quantity < 0 || price < 0) {
                        throw new IOException("Invalid data at line " + lineNumber + " (negative quantity or price): " + line);
                    }
                    records.add(new InventoryRecord(sku, productName, quantity, price));
                } catch (NumberFormatException e) {
                    throw new IOException("Number format error at line " + lineNumber + ": " + line, e);
                }
            }
        } catch (IOException e) {
            // Re-throw the exception to be handled by the service layer
            throw e;
        }
        return records;
    }

    // Function 3 : Move the file depending on the success Status
    
    public void moveFile(File sourceFile, boolean success) 
    {
        File destinationDir = new File(success ? PROCESSED_DIRECTORY : ERROR_DIRECTORY);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }
        try {
            Files.move(sourceFile.toPath(),
                       Paths.get(destinationDir.getPath(), sourceFile.getName()),
                       StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error moving file " + sourceFile.getName() + ": " + e.getMessage());
        }
    }
}

package com.litmus7.empManagement.utils;

import com.litmus7.empManagement.model.Response;
import com.litmus7.empManagement.constants.StatusCodes;
import com.litmus7.empManagement.exception.EmployeeManagementException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class CSVReader {

    private static List<Response> errors = new ArrayList<>();
    private static final Logger logger = AppLogger.getLogger();

    public static List<String[]> readCSV(String filepath) throws EmployeeManagementException {
        List<String[]> data = new ArrayList<>();
        errors.clear();

        logger.info("Starting CSV file read operation for file: " + filepath);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int lineNumber = 0;

            // Skip header
            br.readLine();
            lineNumber++;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    String errorMsg = "Empty line at line " + lineNumber;
                    logger.warning(errorMsg);
                    errors.add(new Response<>(2, errorMsg));
                    continue;
                }

                String[] values = line.split(",");

                if (values.length != 8) {
                    String errorMsg = "Incomplete row at line " + lineNumber + ": expected 8 columns, got " + values.length;
                    logger.warning(errorMsg);
                    errors.add(new Response<>(2, errorMsg));
                    continue;
                }

                data.add(values);
            }

            logger.info("CSV file read completed. Total valid rows: " + data.size() + ", Errors: " + errors.size());

        } catch (FileNotFoundException e) {
            String errorMsg = "CSV file not found: " + filepath;
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.FILE_NOT_FOUND);
        } catch (IOException e) {
            String errorMsg = "Error reading CSV file: " + e.getMessage();
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.CONNECTION_ERROR);
        } catch (Exception e) {
            String errorMsg = "Unexpected error reading CSV file: " + e.getMessage();
            logger.severe(errorMsg);
            throw new EmployeeManagementException(errorMsg, e, StatusCodes.FAILURE);
        }

        return data.isEmpty() ? null : data;
    }

    public static List<Response> getErrors() {
        return new ArrayList<>(errors);
    }

    public static boolean hasErrors() {
        return !errors.isEmpty();
    }
}

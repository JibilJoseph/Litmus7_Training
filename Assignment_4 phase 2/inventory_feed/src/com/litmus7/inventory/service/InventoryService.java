package com.litmus7.inventory.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.litmus7.inventory.constants.StatusCodes;
import com.litmus7.inventory.dao.InventoryDAO;
import com.litmus7.inventory.exception.InventoryException;
import com.litmus7.inventory.models.InventoryRecord;
import com.litmus7.inventory.utils.Applogger;
import com.litmus7.inventory.utils.CSVProcessor;
import com.litmus7.inventory.utils.DatabaseConfig;


public class InventoryService {

    private InventoryDAO inventoryDAO;
    private CSVProcessor csvProcessor;

    public InventoryService() {
        this.inventoryDAO = new InventoryDAO();
        this.csvProcessor = new CSVProcessor();
        Applogger.setupLogger(); 
    }

    // Function 1 : Process all CSV Files using threads
    
    public int[] ProcessAllCSV() throws InventoryException 
    {
        File[] csvFiles = csvProcessor.getCSVFiles();
        int totalFiles = (csvFiles == null) ? 0 : csvFiles.length;

        if (csvFiles == null || csvFiles.length == 0) {
            throw new InventoryException("No CSV files found to process.", StatusCodes.FILE_NOT_FOUND);
        }

        AtomicInteger processedCount = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();

        for (File csvFile : csvFiles) 
        {
            Runnable task = () -> {
                Connection conn = null;
                boolean success = false;
                try {
                    conn = DatabaseConfig.getConnection();
                    conn.setAutoCommit(false);

                    List<InventoryRecord> records = csvProcessor.parseCSV(csvFile);
                    for (InventoryRecord record : records) 
                    {
                        inventoryDAO.addInventoryItem(conn, record.getSku(), record.getProductName(), record.getQuantity(), record.getPrice());
                    }
                    conn.commit();
                    success = true;
                    Applogger.logInfo("Successfully processed {0}", csvFile.getName());
                } catch (SQLException | IOException e) {
                    if (conn != null) {
                        try {
                            conn.rollback();
                            Applogger.logSevere("Error rolling back transaction for " + csvFile.getName(), e);
                        } catch (SQLException ex) {
                            Applogger.logSevere("Error rolling back transaction for " + csvFile.getName(), ex);
                        }
                    } else {
                        Applogger.logSevere("Error processing file " + csvFile.getName(), e);
                    }
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            Applogger.logSevere("Error closing connection for " + csvFile.getName(), e);
                        }
                    }
                    csvProcessor.moveFile(csvFile, success);
                }

                if (success) {
                    processedCount.incrementAndGet();
                }
            };

            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Applogger.logSevere("Interrupted while waiting for thread to complete.", e);
                Thread.currentThread().interrupt();
            }
        }

        return new int[]{totalFiles, processedCount.get()};
    }
}

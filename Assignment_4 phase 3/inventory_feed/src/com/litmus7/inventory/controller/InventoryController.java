package com.litmus7.inventory.controller;


import com.litmus7.inventory.constants.StatusCodes;
import com.litmus7.inventory.exception.InventoryException;
import com.litmus7.inventory.models.Response;
import com.litmus7.inventory.service.InventoryService;



public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController() {
        this.inventoryService = new InventoryService();
    }

    public Response<String> processAllCSV() {
        try {
            int[] counts = inventoryService.ProcessAllCSV();
            int totalFiles = counts[0];
            int processedCount = counts[1];

            String message = "Processing complete. Total files: " + totalFiles + ", Successfully processed: " + processedCount;
            Response<String> response = new Response<>(StatusCodes.SUCCESS, message);
            response.setTotalCount(totalFiles);
            response.setProcessedCount(processedCount);
            return response;

        } catch (InventoryException e) {
            Response<String> response = new Response<>(e.getStatusCode(), e.getMessage());
            response.setTotalCount(0);
            response.setProcessedCount(0);
            return response;
        }
    }
}
package com.litmus7.inventory.ui;

import com.litmus7.inventory.controller.InventoryController;
import com.litmus7.inventory.models.Response;


public class Main {
    public static void main(String[] args) {
        InventoryController controller = new InventoryController();
        Response<String> response = controller.processAllCSV();

        int error = response.getTotalCount() - response.getProcessedCount();

        System.out.println("\n--- Processing Summary ---");
        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Message: " + response.getMessage());
        System.out.println("Total files found      : " + response.getTotalCount());
        System.out.println("Successfully processed : " + response.getProcessedCount());
        System.out.println("Error Files            : " + error);
        System.out.println("--------------------------");
    }
}
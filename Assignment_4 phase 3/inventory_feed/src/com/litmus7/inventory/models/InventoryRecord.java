package com.litmus7.inventory.models;

public class InventoryRecord {
    private int sku;
    private String productName;
    private int quantity;
    private double price;
    
    public InventoryRecord(int sku, String productName, int quantity, double price) {
        this.sku = sku;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters
    public int getSku() { return sku; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return String.format("InventoryRecord{sku=%d, productName='%s', quantity=%d, price=%.2f}", 
                           sku, productName, quantity, price);
    }
}

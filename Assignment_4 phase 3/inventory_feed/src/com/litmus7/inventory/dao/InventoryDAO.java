package com.litmus7.inventory.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InventoryDAO {

    // Method  1 : to add a new inventory item
    public void addInventoryItem(Connection conn, int sku, String productName, int quantity, double price) throws SQLException {
        String sql = "INSERT INTO inventory (SKU, ProductName, Quantity, Price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sku);
            pstmt.setString(2, productName);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, price);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to add inventory item: " + sku);
            }
        }
    }
}

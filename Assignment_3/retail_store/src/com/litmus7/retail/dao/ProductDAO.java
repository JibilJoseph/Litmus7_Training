package com.litmus7.retail.dao;

import java.util.ArrayList;
import java.util.List;

import com.litmus7.retail.dto.Product;
import com.litmus7.retail.exception.RetailStoreException;


public class ProductDAO {
	
	public boolean addProduct(Product product) throws RetailStoreException
	{
		
		// Function to add a new Product to DB
		return true;
	}
	
	public List<Product> getAllProducts() throws RetailStoreException {
        List<Product> products = new ArrayList<>();
        // Function to implement get all products
        
        return products;
    }
	
	public List<Product> getProductByCategory(String category) throws RetailStoreException
	{
		List<Product> products = new ArrayList<>();
        // Function to implement get Product By Category
        
        return products;
		
	}
	


}

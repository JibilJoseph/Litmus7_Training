package com.litmus7.retail.controller;

import java.util.List;

import com.litmus7.retail.dto.Product;
import com.litmus7.retail.dto.Response;
import com.litmus7.retail.exception.RetailStoreException;
import com.litmus7.retail.service.ProductService;

public class ProductController {
	private ProductService productservice=new ProductService();
	
	// Function 1 : addProduct 
	
	public Response<Boolean> addProduct(Product product) {
        try {
            productservice.addProduct(product);
            return new Response<>(200, "Product added successfully");
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error adding product: " + e.getMessage());
        }
    }
	
	// Function 2 : getAllProducts
	
	public Response<List<Product>> getAllProducts()
	{
		try {
			List<Product> products=productservice.getAllProducts();
			return new Response<>(200,"Sucess",products);
		} catch (RetailStoreException e) {
			return new Response<>(500,"Error"+e.getMessage());
		}
		
	}
	
	// Function 3 : get products by category
	
	public Response<List<Product>> getProductsByCategory(String category) {
        try {
            List<Product> products = productservice.getProductsByCategory(category);
            return new Response<>(200, "Successfully retrieved products for category: " + category, products);
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error getting products by category: " + e.getMessage());
        }
    }
	
	// Function 4 : sort products

    public Response<List<Product>> sortProducts(List<Product> products, String sortBy) {
        try {
            List<Product> sortedProducts = productservice.sortProducts(products, sortBy);
            return new Response<>(200, "Successfully sorted products by " + sortBy, sortedProducts);
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error sorting products: " + e.getMessage());
        }
    }

}

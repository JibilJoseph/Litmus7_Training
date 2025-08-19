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
        	if (product == null) 
            {
                return new Response<>(400, "Product cannot be null", false);
            }
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
	
	// Function 4 : sort products by name ascending

    public Response<List<Product>> sortProductsByNameAsc(List<Product> products)
    {
    	try
    	{
    		List<Product> sortedProducts = productservice.sortProductsByNameAsc(products);
            return new Response<>(200, "Successfully sorted products by name ascending", sortedProducts);
    		
    	}
    	catch(RetailStoreException e)
    	{
    		return new Response<>(500,"Error sorting"+e.getMessage());
    	}
    }
    
 // Function 5 : sort products by name descending

    public Response<List<Product>> sortProductsByNameDesc(List<Product> products) {
        try {
            List<Product> sortedProducts = productservice.sortProductsByNameDesc(products);
            return new Response<>(200, "Successfully sorted products by name descending", sortedProducts);
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error sorting products: " + e.getMessage());
        }
    }

    // Function 6 : sort products by price ascending

    public Response<List<Product>> sortProductsByPriceAsc(List<Product> products) {
        try {
            List<Product> sortedProducts = productservice.sortProductsByPriceAsc(products);
            return new Response<>(200, "Successfully sorted products by price ascending", sortedProducts);
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error sorting products: " + e.getMessage());
        }
    }

    // Function 7 : sort products by price descending

    public Response<List<Product>> sortProductsByPriceDesc(List<Product> products) {
        try {
            List<Product> sortedProducts = productservice.sortProductsByPriceDesc(products);
            return new Response<>(200, "Successfully sorted products by price descending", sortedProducts);
        } catch (RetailStoreException e) {
            return new Response<>(500, "Error sorting products: " + e.getMessage());
        }
    }

}

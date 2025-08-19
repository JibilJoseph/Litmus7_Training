package com.litmus7.retail.dto;

public class Product {
	
	private int productId;
    private String productName;
    private double price;
    private String category;
    private ProductStatus status;
    
    
    public Product(int productId,String productName,double price,ProductStatus status,String category)
    {
    	this.productId=productId;
    	this.productName=productName;
    	this.price=price;
    	this.status=status;
		this.category=category;
    }


	public int getProductId() {
		return productId;
	}


	public String getProductName() {
		return productName;
	}


	public double getPrice() {
		return price;
	}


	public ProductStatus getStatus() {
		return status;
	}


	public String getCategory() {
		return category;
	}


	

}


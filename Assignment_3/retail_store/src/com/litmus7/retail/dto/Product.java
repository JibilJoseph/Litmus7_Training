package com.litmus7.retail.dto;

public class Product {
	
	private int productId;
    private String productName;
    private double price;
    private String status;
    
    
    public Product(int productId,String productName,double price,String status)
    {
    	this.productId=productId;
    	this.productName=productName;
    	this.price=price;
    	this.status=status;
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


	public String getStatus() {
		return status;
	}

}


package com.litmus7.retail.dto;

public class Electronics extends Product {
	public String brand;
    public int warrantyMonths;
    
	public Electronics(int productId, String productName, double price, String status,String brand,int warrantyMonths) {
		super(productId, productName, price, status);
		this.brand=brand;
		this.warrantyMonths=warrantyMonths;
	}
	

}

package com.litmus7.retail.dto;

public class Clothing extends Product {
	
	public String size;
    public String material;
	
	public Clothing(int productId, String productName, double price, String status,String size,String material) {
		super(productId, productName, price, status);
		this.size=size;
		this.material=material;
		
	}
	

}

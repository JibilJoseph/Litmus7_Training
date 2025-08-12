package com.litmus7.retail.dto;

import java.util.Date;

public class Grocery extends Product {
	public Date expiryDate;
	public double weightKg;
	
	public Grocery(int productId, String productName, double price, String status,Date expiryDate,double weightKg) {
		super(productId, productName, price, status);
		this.expiryDate=expiryDate;
		this.weightKg=weightKg;
		
	}
	

}

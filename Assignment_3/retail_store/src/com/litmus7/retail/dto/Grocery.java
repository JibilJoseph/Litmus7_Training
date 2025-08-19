package com.litmus7.retail.dto;

import java.util.Date;

public class Grocery extends Product {
	
	public Date expiryDate;
	public double weightKg;
	
	public Grocery(int productId, String productName, double price, ProductStatus status,Date expiryDate,double weightKg,String category) {
		super(productId, productName, price, status,category);
		this.expiryDate=expiryDate;
		this.weightKg=weightKg;
		
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}

	

}

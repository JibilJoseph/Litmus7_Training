package com.litmus7.retail.dto;

public class Electronics extends Product {
	
	public String brand;
    public int warrantyMonths;
    
	public Electronics(int productId, String productName, double price, ProductStatus status,String brand,int warrantyMonths,String category) {
		super(productId, productName, price, status,category);
		this.brand=brand;
		this.warrantyMonths=warrantyMonths;
	}
	
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getWarrantyMonths() {
		return warrantyMonths;
	}

	public void setWarrantyMonths(int warrantyMonths) {
		this.warrantyMonths = warrantyMonths;
	}

	

}

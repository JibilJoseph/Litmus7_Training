package com.litmus7.retail.dto;

public class Clothing extends Product {
	
	public String size;
    public String material;
	
	public Clothing(int productId, String productName, double price, ProductStatus status,String size,String material,String category) {
		super(productId, productName, price, status,category);
		this.size=size;
		this.material=material;
		
	}
	
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	

}

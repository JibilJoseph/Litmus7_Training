package com.litmus7.retail.service;

import java.util.List;

import com.litmus7.retail.dao.ProductDAO;
import com.litmus7.retail.dto.Product;
import com.litmus7.retail.exception.RetailStoreException;
import com.litmus7.retail.util.ProductSorter;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();
    private ProductSorter productSorter=new ProductSorter();

    public boolean addProduct(Product product) throws RetailStoreException {
        productDAO.addProduct(product);
        // Function not implemented
        return true;
    }

    public List<Product> getAllProducts() throws RetailStoreException {
        return productDAO.getAllProducts();
    }

    public List<Product> getProductsByCategory(String category) throws RetailStoreException {
        return productDAO.getProductByCategory(category);
    }

   

    public List<Product> sortProductsByNameAsc(List<Product> products) throws RetailStoreException {
    	productSorter.sort(products, "name_asc");
        return products;
    }

    public List<Product> sortProductsByNameDesc(List<Product> products) throws RetailStoreException {
    	productSorter.sort(products, "name_desc");
        return products;
    }

    public List<Product> sortProductsByPriceAsc(List<Product> products) throws RetailStoreException {
    	productSorter.sort(products, "price_asc");
        return products;
    }

    public List<Product> sortProductsByPriceDesc(List<Product> products) throws RetailStoreException {
    	productSorter.sort(products, "price_desc");
        return products;
    }
}


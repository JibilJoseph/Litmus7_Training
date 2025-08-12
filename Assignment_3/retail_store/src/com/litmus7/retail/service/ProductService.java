package com.litmus7.retail.service;

import java.util.List;

import com.litmus7.retail.dao.ProductDAO;
import com.litmus7.retail.dto.Product;
import com.litmus7.retail.exception.RetailStoreException;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

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

    public List<Product> sortProducts(List<Product> products, String sortBy) throws RetailStoreException {
        return productDAO.sortProducts(products, sortBy);
    }
}


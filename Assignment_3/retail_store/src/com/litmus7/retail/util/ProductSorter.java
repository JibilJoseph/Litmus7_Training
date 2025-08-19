package com.litmus7.retail.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.litmus7.retail.dto.Product;

public class ProductSorter {

	public void sort(List<Product> products, String sortBy) {
        Comparator<Product> comparator = null;

        switch (sortBy.toLowerCase()) {
            case "price_asc":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "price_desc":
                comparator = Comparator.comparing(Product::getPrice).reversed();
                break;
            case "name_asc":
                comparator = Comparator.comparing(Product::getProductName);
                break;
            case "name_desc":
                comparator = Comparator.comparing(Product::getProductName).reversed();
                break;
            default:
                // No sorting
                return;
        }

        Collections.sort(products, comparator);
    }

}

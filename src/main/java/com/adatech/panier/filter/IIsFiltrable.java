package com.adatech.panier.filter;

import java.util.ArrayList;
import java.util.List;

import com.adatech.panier.model.Category;
import com.adatech.panier.model.Product;

public interface IIsFiltrable {
    default List<Product> filterByName(List<Product> products, String name) {
        if (products == null) {
            return new ArrayList<>();
        }
        if (name == null || name.isBlank()) {
            return products;
        }

        String needle = name.toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p == null)
                continue;
            String pname = p.getName();
            if (pname != null && pname.toLowerCase().contains(needle)) {
                result.add(p);
            }
        }
        return result;
    }

    default List<Product> filterByCategory(List<Product> products, Category category) {
        if (products == null) {
            return new ArrayList<>();
        }
        if (category == null || category.getId() == null) {
            return products;
        }

        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p == null)
                continue;
            if (p.getCategory() != null && p.getCategory().getId() != null
                    && p.getCategory().getId().equals(category.getId())) {
                result.add(p);
            }
        }
        return result;
    }

    default List<Product> filterByPrice(List<Product> products, int price) {
        if (products == null) {
            return new ArrayList<>();
        }
        if (price <= 0) {
            return products;
        }

        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p == null)
                continue;
            if (p.getPrice() == price) {
                result.add(p);
            }
        }
        return result;
    }
}

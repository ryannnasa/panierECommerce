package com.adatech.panier.service;

import com.adatech.panier.filter.IIsFiltrable;
import com.adatech.panier.model.Product;

import java.util.List;

public class ProductService {
    private IIsFiltrable filterImpl;

    public ProductService(IIsFiltrable filterImpl) {
        this.filterImpl = filterImpl;
    }

    public List<Product> search(List<Product> products, Product prototype) {
        if (filterImpl == null)
            return products;
        if (prototype == null)
            return products;

        List<Product> result = filterImpl.filterByName(products, prototype.getName());
        result = filterImpl.filterByCategory(result, prototype.getCategory());
        result = filterImpl.filterByPrice(result, prototype.getPrice());
        return result;
    }
}

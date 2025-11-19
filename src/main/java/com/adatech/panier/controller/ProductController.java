package com.adatech.panier.controller;

import com.adatech.panier.model.Product;
import com.adatech.panier.service.ProductService;

import java.util.List;

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public List<Product> listProducts(List<Product> products, Product prototype) {
        return service.search(products, prototype);
    }
}

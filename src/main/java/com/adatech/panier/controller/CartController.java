package com.adatech.panier.controller;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.Product;
import com.adatech.panier.service.CartService;

public class CartController {
    private CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    public void addToCart(Cart cart, Product product, int qty) {
        service.addToCart(cart, product, qty);
    }

    public void removeFromCart(Cart cart, Product product) {
        service.removeFromCart(cart, product);
    }
}

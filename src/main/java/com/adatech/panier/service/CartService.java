package com.adatech.panier.service;

import com.adatech.panier.command.AddToCartCommand;
import com.adatech.panier.command.CartInvoker;
import com.adatech.panier.command.RemoveFromCartCommand;
import com.adatech.panier.model.Cart;
import com.adatech.panier.model.Product;

public class CartService {
    private CartInvoker invoker = new CartInvoker();

    public void addToCart(Cart cart, Product product, int qty) {
        AddToCartCommand cmd = new AddToCartCommand(cart, product, qty);
        invoker.executeCommand(cmd);
    }

    public void removeFromCart(Cart cart, Product product) {
        RemoveFromCartCommand cmd = new RemoveFromCartCommand(cart, product);
        invoker.executeCommand(cmd);
    }

    public void undoLast() {
        invoker.undoLast();
    }
}

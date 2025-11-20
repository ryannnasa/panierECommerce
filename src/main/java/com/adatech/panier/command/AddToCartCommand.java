package com.adatech.panier.command;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.Product;

public class AddToCartCommand implements ICommandCancelable {
    private Cart cart;
    private Product product;
    private int qty;

    public AddToCartCommand(Cart cart, Product product, int qty) {
        this.cart = cart;
        this.product = product;
        this.qty = qty;
    }

    @Override
    public void execute() {
        cart.addItem(product, qty);
    }

    @Override
    public void undo() {
        cart.removeItem(product);
    }
}

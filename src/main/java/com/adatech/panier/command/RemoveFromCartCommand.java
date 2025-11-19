package com.adatech.panier.command;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.Product;
import com.adatech.panier.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class RemoveFromCartCommand implements ICommandCancelable {
    private Cart cart;
    private Product product;
    private List<CartItem> removedItems = new ArrayList<>();

    public RemoveFromCartCommand(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    @Override
    public void execute() {
        removedItems.clear();
        for (CartItem ci : cart.getItems()) {
            if (ci.getProduct().getId().equals(product.getId())) {
                removedItems.add(new CartItem(ci.getProduct(), ci.getQuantity()));
            }
        }
        cart.removeItem(product);
    }

    @Override
    public void undo() {
        for (CartItem ci : removedItems) {
            cart.addItem(ci.getProduct(), ci.getQuantity());
        }
    }

}

package com.adatech.panier.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private UUID id;
    private List<CartItem> items = new ArrayList<>();

    public Cart(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Product p, int qty) {
        items.add(new CartItem(p, qty));
    }

    public void removeItem(Product p) {
        items.removeIf(i -> i.getProduct().getId().equals(p.getId()));
    }

    public int getTotal() {
        return items.stream().mapToInt(CartItem::getSubtotal).sum();
    }
}

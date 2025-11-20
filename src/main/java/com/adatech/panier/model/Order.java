package com.adatech.panier.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private Client client;
    private Cart cart;
    private List<OrderItem> items;
    private int total;

    public Order(UUID id, Client client, Cart cart, List<OrderItem> items, int total) {
        this.id = id;
        this.client = client;
        this.cart = cart;
        this.items = items;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Cart getCart() {
        return cart;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}

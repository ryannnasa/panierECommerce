package com.adatech.panier.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private Client client;
    private List<OrderItem> items;
    private int total;

    public Order(UUID id, Client client, List<OrderItem> items, int total) {
        this.id = id;
        this.client = client;
        this.items = items;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}

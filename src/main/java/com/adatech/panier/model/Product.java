package com.adatech.panier.model;

import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private int price;
    private int stockQty;
    private Category category;

    public Product(UUID id, String name, int price, int stockQty, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQty = stockQty;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQty() {
        return stockQty;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return stockQty > 0;
    }
}

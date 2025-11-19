package com.adatech.panier.model;

public class OrderItem {
    private Product product;
    private int quantity;
    private int priceAtOrder;

    public OrderItem(Product product, int quantity, int priceAtOrder) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPriceAtOrder() {
        return priceAtOrder;
    }
}

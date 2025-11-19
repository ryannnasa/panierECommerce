package com.adatech.panier.model;

import java.util.UUID;

public class Client {
    private UUID id;
    private String name;
    private String email;
    private Cart cart;

    public Client(UUID id, String name, String email, Cart cart) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cart = cart;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Cart getCart() {
        return cart;
    }
}

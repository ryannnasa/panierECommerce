package com.adatech.panier.controller;

import com.adatech.panier.model.Client;
import com.adatech.panier.service.OrderService;

public class OrderController {
    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    public void checkout(Client client) {
    }
}

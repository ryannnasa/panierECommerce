package com.adatech.panier.service;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.CartItem;
import com.adatech.panier.model.Client;
import com.adatech.panier.model.Order;
import com.adatech.panier.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {
    public Order placeOrder(Client client, Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Le panier ne peut pas être vide");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        int total = cart.getTotal();
        Order order = new Order(UUID.randomUUID(), client, cart, orderItems, total);

        return order;
    }
}

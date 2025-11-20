package com.adatech.panier.service;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.CartItem;
import com.adatech.panier.model.Client;
import com.adatech.panier.model.Order;
import com.adatech.panier.model.OrderItem;
import com.adatech.panier.observer.IOrderObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private List<IOrderObserver> observers = new ArrayList<>();

    public void addObserver(IOrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IOrderObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Order order) {
        for (IOrderObserver observer : observers) {
            observer.onOrderPlaced(order);
        }
    }

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

        // Notifier les observateurs (notamment l'entrepôt)
        notifyObservers(order);

        return order;
    }
}

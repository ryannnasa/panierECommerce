package com.adatech.panier.observer;

import com.adatech.panier.model.Order;

public interface IOrderObserver {
    void onOrderPlaced(Order order);
}

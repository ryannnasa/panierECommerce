package com.adatech.panier.observer;

import com.adatech.panier.model.Order;
import com.adatech.panier.model.OrderItem;

public class WarehouseObserver implements IOrderObserver {
    private String warehouseName;

    public WarehouseObserver(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public void onOrderPlaced(Order order) {
        System.out.println("\n[" + warehouseName + "] Nouvelle commande reçue !");
        System.out.println("  Commande ID: " + order.getId());
        System.out.println("  Client: " + order.getClient().getName());
        System.out.println("  Articles à préparer:");

        for (OrderItem item : order.getItems()) {
            System.out.printf("    - %s x%d\n",
                    item.getProduct().getName(),
                    item.getQuantity());
        }

        System.out.println("  Total: " + order.getTotal() + " EUR");
        System.out.println("  Statut: En cours de préparation...");
    }

    public String getWarehouseName() {
        return warehouseName;
    }
}

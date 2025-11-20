package com.adatech.panier;

import com.adatech.panier.model.Cart;
import com.adatech.panier.model.CartItem;
import com.adatech.panier.model.Category;
import com.adatech.panier.model.Client;
import com.adatech.panier.model.Order;
import com.adatech.panier.model.Product;
import com.adatech.panier.service.OrderService;
import com.adatech.panier.command.AddToCartCommand;
import com.adatech.panier.command.RemoveFromCartCommand;
import com.adatech.panier.command.CartInvoker;
import com.adatech.panier.observer.WarehouseObserver;

import java.util.UUID;

public class App {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE APPLICATION DEMO ===\n");

        // === 1. CRÉATION DES CATÉGORIES ===
        System.out.println("1. CRÉATION DES CATÉGORIES");
        Category electronics = new Category(UUID.randomUUID(), "Electronics");
        Category books = new Category(UUID.randomUUID(), "Books");
        Category home = new Category(UUID.randomUUID(), "Home");
        System.out.println("✓ Categories créées: Electronics, Books, Home\n");

        // === 2. CRÉATION DES PRODUITS ===
        System.out.println("2. CRÉATION DES PRODUITS");
        Product phone = new Product(UUID.randomUUID(), "Phone", 499, 10, electronics);
        Product laptop = new Product(UUID.randomUUID(), "Laptop", 1299, 5, electronics);
        Product tablet = new Product(UUID.randomUUID(), "Tablet", 399, 8, electronics);
        Product smartwatch = new Product(UUID.randomUUID(), "Smartwatch", 199, 0, electronics);

        Product book = new Product(UUID.randomUUID(), "Clean Code", 35, 20, books);

        Product coffeeMaker = new Product(UUID.randomUUID(), "Coffee Maker", 79, 9, home);

        System.out.println("✓ 7 produits créés");
        System.out.println("  - Smartwatch disponible: " + smartwatch.isAvailable() + " (stock: 0)");
        System.out.println("  - Phone disponible: " + phone.isAvailable() + " (stock: 10)\n");

        // === 3. CRÉATION CLIENT ET PANIER ===
        System.out.println("3. CRÉATION CLIENT ET PANIER");
        Cart cart = new Cart(UUID.randomUUID());
        Client client = new Client(UUID.randomUUID(), "Alice Dubois", "alice@example.com", cart);
        System.out.println("✓ Client créé: " + client.getName() + " (" + client.getEmail() + ")\n");

        // === 4. GESTION DU PANIER AVEC PATTERN COMMAND ===
        System.out.println("4. GESTION DU PANIER (Pattern Command)");
        CartInvoker invoker = new CartInvoker();

        System.out.println("Ajout de 2 Phones au panier...");
        invoker.executeCommand(new AddToCartCommand(cart, phone, 2));

        System.out.println("Ajout de 1 Clean Code au panier...");
        invoker.executeCommand(new AddToCartCommand(cart, book, 1));

        System.out.println("Ajout de 1 Laptop au panier...");
        invoker.executeCommand(new AddToCartCommand(cart, laptop, 1));

        System.out.println("\nContenu du panier:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.printf("  - %s x%d : %d EUR (subtotal: %d EUR)\n",
                    p.getName(), item.getQuantity(), p.getPrice(), item.getSubtotal());
        }
        System.out.println("Total du panier: " + cart.getTotal() + " EUR\n");

        // === 5. TEST UNDO ===
        System.out.println("5. TEST DE LA FONCTIONNALITÉ UNDO");
        System.out.println("Annulation de la dernière commande (retrait du Laptop)...");
        invoker.undoLast();

        System.out.println("Contenu du panier après undo:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.printf("  - %s x%d : %d EUR (subtotal: %d EUR)\n",
                    p.getName(), item.getQuantity(), p.getPrice(), item.getSubtotal());
        }
        System.out.println("Total: " + cart.getTotal() + " EUR\n");

        // === 6. TEST SUPPRESSION ET UNDO ===
        System.out.println("6. TEST SUPPRESSION ET UNDO");
        System.out.println("Suppression des Phones du panier...");
        invoker.executeCommand(new RemoveFromCartCommand(cart, phone));

        System.out.println("Contenu après suppression:");
        for (CartItem item : cart.getItems()) {
            System.out.printf("  - %s x%d\n", item.getProduct().getName(), item.getQuantity());
        }

        System.out.println("\nAnnulation de la suppression (undo)...");
        invoker.undoLast();

        System.out.println("Contenu après undo de la suppression:");
        for (CartItem item : cart.getItems()) {
            System.out.printf("  - %s x%d\n", item.getProduct().getName(), item.getQuantity());
        }
        System.out.println();

        // === 7. AJOUT DE PRODUITS SUPPLÉMENTAIRES ===
        System.out.println("7. AJOUT DE PRODUITS SUPPLÉMENTAIRES");
        invoker.executeCommand(new AddToCartCommand(cart, tablet, 1));
        invoker.executeCommand(new AddToCartCommand(cart, coffeeMaker, 1));

        System.out.println("Contenu final du panier:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.printf("  - %s x%d : %d EUR (subtotal: %d EUR)\n",
                    p.getName(), item.getQuantity(), p.getPrice(), item.getSubtotal());
        }
        System.out.println("Total final: " + cart.getTotal() + " EUR\n");

        // === 8. CRÉATION DE COMMANDE (OrderService avec Observer) ===
        System.out.println("8. CRÉATION DE COMMANDE (Pattern Observer)");
        OrderService orderService = new OrderService();

        // Enregistrement de l'entrepôt comme observateur
        WarehouseObserver warehouse = new WarehouseObserver("Entrepôt Central Paris");
        orderService.addObserver(warehouse);
        System.out.println("✓ Observateur enregistré: " + warehouse.getWarehouseName());

        System.out.println("\nPassage de la commande...");
        Order order = orderService.placeOrder(client, cart);

        System.out.println("✓ Commande créée:");
        System.out.println("  ID: " + order.getId());
        System.out.println("  Client: " + order.getClient().getName());
        System.out.println("  Nombre d'articles: " + order.getItems().size());
        System.out.println("  Total: " + order.getTotal() + " EUR");
        System.out.println("  Panier associé: " + order.getCart().getId());

        System.out.println("\nDétail des articles de la commande:");
        order.getItems().forEach(item -> {
            System.out.printf("  - %s x%d à %d EUR = %d EUR\n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPriceAtOrder(),
                    item.getQuantity() * item.getPriceAtOrder());
        });

        // === 9. TEST DE VALIDATION (panier vide) ===
        System.out.println("\n9. TEST DE VALIDATION (panier vide)");
        Cart emptyCart = new Cart(UUID.randomUUID());
        Client client2 = new Client(UUID.randomUUID(), "Bob Martin", "bob@example.com", emptyCart);

        try {
            orderService.placeOrder(client2, emptyCart);
            System.out.println("✗ Erreur: la commande avec panier vide devrait échouer");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation réussie: " + e.getMessage());
        }

        System.out.println("\n=== DEMO TERMINÉE ===");
    }
}

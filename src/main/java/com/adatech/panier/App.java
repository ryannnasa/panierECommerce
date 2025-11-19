package com.adatech.panier;

import com.adatech.panier.filter.IIsFiltrable;
import com.adatech.panier.model.Cart;
import com.adatech.panier.model.CartItem;
import com.adatech.panier.model.Category;
import com.adatech.panier.model.Client;
import com.adatech.panier.model.Order;
import com.adatech.panier.model.OrderItem;
import com.adatech.panier.model.Product;
import com.adatech.panier.service.ProductService;
import com.adatech.panier.command.AddToCartCommand;
import com.adatech.panier.command.RemoveFromCartCommand;
import com.adatech.panier.command.CartInvoker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class App {
    public static void main(String[] args) {

        Category electronics = new Category(UUID.randomUUID(), "Electronics");
        Category books = new Category(UUID.randomUUID(), "Books");
        Category home = new Category(UUID.randomUUID(), "Home");

        Product phone = new Product(UUID.randomUUID(), "Phone", 499, 10, electronics);
        Product laptop = new Product(UUID.randomUUID(), "Laptop", 1299, 5, electronics);
        Product tablet = new Product(UUID.randomUUID(), "Tablet", 399, 8, electronics);
        Product headphones = new Product(UUID.randomUUID(), "Headphones", 89, 15, electronics);
        Product smartwatch = new Product(UUID.randomUUID(), "Smartwatch", 199, 12, electronics);

        Product book = new Product(UUID.randomUUID(), "Clean Code", 35, 20, books);
        Product algorithms = new Product(UUID.randomUUID(), "Algorithms", 45, 7, books);
        Product designPatterns = new Product(UUID.randomUUID(), "Design Patterns", 50, 6, books);

        Product coffeeMaker = new Product(UUID.randomUUID(), "Coffee Maker", 79, 9, home);
        Product blender = new Product(UUID.randomUUID(), "Blender", 59, 11, home);

        Cart cart = new Cart(UUID.randomUUID());
        CartInvoker invoker = new CartInvoker();

        // Use commands to manipulate the cart
        invoker.executeCommand(new AddToCartCommand(cart, phone, 2));
        invoker.executeCommand(new AddToCartCommand(cart, book, 1));

        System.out.println("Cart contents:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int subtotal = item.getSubtotal();
            System.out.printf(" - %s x%d : %d EUR (subtotal %d EUR)\n", p.getName(), qty, p.getPrice(), subtotal);
        }

        int total = cart.getTotal();
        System.out.println("Cart total: " + total + " EUR");

        // Undo last add (removes book)
        System.out.println("\nUndo last command (should remove last added product)...");
        invoker.undoLast();
        System.out.println("Cart contents after undo:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            int qty = item.getQuantity();
            int subtotal = item.getSubtotal();
            System.out.printf(" - %s x%d : %d EUR (subtotal %d EUR)\n", p.getName(), qty, p.getPrice(), subtotal);
        }

        // Remove product (phone) using command and then undo
        System.out.println("\nRemove 'Phone' using command...");
        invoker.executeCommand(new RemoveFromCartCommand(cart, phone));
        System.out.println("Cart contents after removal:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.printf(" - %s x%d\n", p.getName(), item.getQuantity());
        }

        System.out.println("\nUndo last command (should restore removed items)...");
        invoker.undoLast();
        System.out.println("Cart contents after undo remove:");
        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            System.out.printf(" - %s x%d\n", p.getName(), item.getQuantity());
        }

        Client client = new Client(UUID.randomUUID(), "Alice", "alice@example.com", cart);

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            orderItems.add(new OrderItem(ci.getProduct(), ci.getQuantity(), ci.getProduct().getPrice()));
        }

        Order order = new Order(UUID.randomUUID(), client, orderItems, total);
        System.out.println(
                "Created order for " + order.getClient().getName() + " with total " + order.getTotal() + " EUR");

        List<Product> catalog = List.of(
                phone, laptop, tablet, headphones, smartwatch,
                book, algorithms, designPatterns,
                coffeeMaker, blender);
        ProductService productService = new ProductService(new IIsFiltrable() {
        });

        Product prototypeByName = new Product(null, "Phone", 0, 0, null);
        List<Product> foundByName = productService.search(catalog, prototypeByName);
        System.out.println("Search results for prototype name 'Phone':");
        for (Product p : foundByName) {
            System.out.println(" - " + p.getName() + " (" + p.getPrice() + " EUR)");
        }

        Product prototypeByCategory = new Product(null, null, 0, 0, electronics);
        List<Product> foundByCategory = productService.search(catalog, prototypeByCategory);
        System.out.println("\nSearch results for category 'Electronics':");
        for (Product p : foundByCategory) {
            System.out.println(" - " + p.getName() + " (" + p.getPrice() + " EUR)");
        }

        Product prototypeByPrice = new Product(null, null, 1299, 0, null);
        List<Product> foundByPrice = productService.search(catalog, prototypeByPrice);
        System.out.println("\nSearch results for price 1299 EUR:");
        for (Product p : foundByPrice) {
            System.out.println(" - " + p.getName() + " (" + p.getPrice() + " EUR)");
        }

        Product prototypeCombined = new Product(null, "Lap", 0, 0, electronics);
        List<Product> foundCombined = productService.search(catalog, prototypeCombined);
        System.out.println("\nCombined search (name 'Lap' + category Electronics):");
        for (Product p : foundCombined) {
            System.out.println(" - " + p.getName() + " (" + p.getPrice() + " EUR)");
        }

        System.out.println("\nDone.");
    }
}

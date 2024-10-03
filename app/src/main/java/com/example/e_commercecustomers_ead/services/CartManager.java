package com.example.e_commercecustomers_ead.services;

import com.example.e_commercecustomers_ead.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
//    private List<Product> cartItems;
//
//    private CartManager() {
//        cartItems = new ArrayList<>();
//    }
    private Map<Product, Integer> cartItems;

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product, int quantity) {
        /*cartItems.add(product);
        product.setInCart(true);*/
        if (cartItems.containsKey(product)) {
            // If the product is already in the cart, increase the quantity
            cartItems.put(product, cartItems.get(product) + quantity);
        } else {
            // If the product is not in the cart, add it with the given quantity
            cartItems.put(product, quantity);
            product.setInCart(true);
        }
    }

    public void removeFromCart(Product product) {
        if (cartItems.containsKey(product)) {
            cartItems.remove(product);
            product.setInCart(false);
        }
    }

    /*public boolean isInCart(Product product) {
        return cartItems.contains(product);
    }

    public List<Product> getCartItems() {
        return cartItems;
    }*/
    public boolean isInCart(Product product) {
        return cartItems.containsKey(product);
    }

    public int getQuantity(Product product) {
        return cartItems.getOrDefault(product, 0);
    }

    public List<Product> getCartItems() {
        return new ArrayList<>(cartItems.keySet());
    }

    public int getTotalItems() {
        return cartItems.size();
    }

    public void clearCart() {
        for (Product product : cartItems.keySet()) {
            product.setInCart(false);
        }
        cartItems.clear();
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalCost += product.getPrice() * quantity;
        }
        return totalCost;
    }
}


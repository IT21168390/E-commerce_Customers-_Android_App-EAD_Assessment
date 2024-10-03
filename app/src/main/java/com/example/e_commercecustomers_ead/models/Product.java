package com.example.e_commercecustomers_ead.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String category;
    private String description;
    private String vendorName;
    private double rating;
    private double price;
    private int imageResource;
    //private String imageUrl;
    private int stock;
    private boolean isInCart;  // New field to track cart status

    public Product(String name, String category, String description, String vendorName, double rating, double price, int imageResource, int stock) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.vendorName = vendorName;
        this.rating = rating;
        this.price = price;
        this.imageResource = imageResource;
        this.isInCart = false;  // Initially not in cart
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getVendorName() {
        return vendorName;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getImageResource() {
        return imageResource;
    }

    // Getter and setter for isInCart
    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }
}

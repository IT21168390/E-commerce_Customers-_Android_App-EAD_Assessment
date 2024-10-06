package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;
import java.util.Date;

public class ProductDataModel implements Serializable {
    private String Id;
    private String ProductId;
    private String Name;
    private String Category;
    private String VendorId;
    private String vendorName;
    private double rating;
    private int stock;
    private double Price;
    private String Description;
    private int imageResource;
    private String Status;
    private String CreatedAt;
    private String UpdatedAt;
    private boolean isInCart;  // New field to track cart status

    public ProductDataModel(String id, String productId, String name, String category, String vendorId, double price, String description, int imageResource, String status, String createdAt, String updatedAt) {
        Id = id;
        ProductId = productId;
        Name = name;
        Category = category;
        VendorId = vendorId;
        vendorName = "VENDOR NAME";
        rating = 4.0;
        stock = 15;
        Price = price;
        Description = description;
        this.imageResource = imageResource;
        Status = status;
        CreatedAt = createdAt;
        UpdatedAt = updatedAt;
        isInCart = false;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }
}

package com.example.e_commercecustomers_ead.models;

import com.example.e_commercecustomers_ead.api_models.ProductDataModel;

public class CartItem {
    private String productId;
    private int quantity;

    public CartItem(String product, int quantity) {
        this.productId = product;
        this.quantity = quantity;
    }

    public String getProduct() {
        return productId;
    }

    public void setProduct(String product) {
        this.productId = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
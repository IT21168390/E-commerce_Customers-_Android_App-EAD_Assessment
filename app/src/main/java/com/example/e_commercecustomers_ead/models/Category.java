package com.example.e_commercecustomers_ead.models;

public class Category {

    private String name;
    private int iconResId;

    public Category(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
package com.example.e_commercecustomers_ead.models;

import java.util.Date;

public class Notification {
    private String message;
    private Date createdAt;

    public Notification(String message, Date createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

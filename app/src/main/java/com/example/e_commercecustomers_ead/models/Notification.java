package com.example.e_commercecustomers_ead.models;

import java.util.Date;

public class Notification {
    private String id;
    private String message;
    private Date createdAt;
    private boolean isRead;

    public Notification(String id, String message, Date createdAt, boolean isRead) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
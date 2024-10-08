package com.example.e_commercecustomers_ead.models;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private String orderId;
    private double orderCost;
    private String orderStatus; // "Pending", "Dispatched", "Delivered", "Cancelled"
    private Date orderDate;
    private String orderDetails; // Detailed information about the order
    private boolean isRated;
    public Order(String orderId, double orderCost, String orderStatus, Date orderDate, String orderDetails) {
        this.orderId = orderId;
        this.orderCost = orderCost;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.isRated = false;
    }

    // Getters and Setters

    public String getOrderId() {
        return orderId;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public boolean isRated() {
        return isRated;
    }

    public void setRated(boolean rated) {
        isRated = rated;
    }

}

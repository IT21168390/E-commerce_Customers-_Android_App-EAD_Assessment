package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    public String Id;

    public String OrderID;

    public String CustomerId;

    public String CustomerName;

    public String OrderStatus;

    public List<OrderItem> OrderItems;

    public double TotalAmount;

    public String Note;

    public Address ShippingAddress;

    public String PlacedAt;

    public String UpdatedAt;

    public List<VendorOrderStatus> VendorStatus;

    public OrderModel(String id, String orderID, String customerId, String customerName, String orderStatus, List<OrderItem> orderItems, double totalAmount, String note, Address shippingAddress, String placedAt, String updatedAt, List<VendorOrderStatus> vendorStatus) {
        Id = id;
        OrderID = orderID;
        CustomerId = customerId;
        CustomerName = customerName;
        OrderStatus = orderStatus;
        OrderItems = orderItems;
        TotalAmount = totalAmount;
        Note = note;
        ShippingAddress = shippingAddress;
        PlacedAt = placedAt;
        UpdatedAt = updatedAt;
        VendorStatus = vendorStatus;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        OrderItems = orderItems;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public Address getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public String getPlacedAt() {
        return PlacedAt;
    }

    public void setPlacedAt(String placedAt) {
        PlacedAt = placedAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public List<VendorOrderStatus> getVendorStatus() {
        return VendorStatus;
    }

    public void setVendorStatus(List<VendorOrderStatus> vendorStatus) {
        VendorStatus = vendorStatus;
    }
}


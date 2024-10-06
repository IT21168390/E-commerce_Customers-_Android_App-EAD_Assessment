package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;
import java.util.Date;
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

    public Date PlacedAt;

    public Date UpdatedAt;

    public List<VendorOrderStatus> VendorStatus;

    public OrderModel(String id, String orderID, String customerId, String customerName, String orderStatus, List<OrderItem> orderItems, double totalAmount, String note, Address shippingAddress, Date placedAt, Date updatedAt, List<VendorOrderStatus> vendorStatus) {
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

    public Date getPlacedAt() {
        return PlacedAt;
    }

    public void setPlacedAt(Date placedAt) {
        PlacedAt = placedAt;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public List<VendorOrderStatus> getVendorStatus() {
        return VendorStatus;
    }

    public void setVendorStatus(List<VendorOrderStatus> vendorStatus) {
        VendorStatus = vendorStatus;
    }
}

class OrderItem
{
    public String ProductId;

    public String ProductName;

    public int Quantity;

    public double Price;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}

class VendorOrderStatus
{
    public String VendorId;

    public String VendorName;

    public String Status;

    public boolean Rated;

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String vendorId) {
        VendorId = vendorId;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public boolean isRated() {
        return Rated;
    }

    public void setRated(boolean rated) {
        Rated = rated;
    }
}

class Address
{
    public String Street;
    public String City;
    public String ZipCode;

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }
}
package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;
import java.util.List;

public class PlaceOrderModel implements Serializable {
    public String CustomerId;
    public List<OrderItemDto> OrderItems;
    public AddressDto ShippingAddress;

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public List<OrderItemDto> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        OrderItems = orderItems;
    }

    public AddressDto getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        ShippingAddress = shippingAddress;
    }
}

class OrderItemDto
{
    public String ProductId;
    public int Quantity;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}

class AddressDto
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
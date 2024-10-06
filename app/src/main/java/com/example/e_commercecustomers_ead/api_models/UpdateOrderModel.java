package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;
import java.util.List;

public class UpdateOrderModel implements Serializable {
    //public List<OrderItemDto> OrderItems;
    public AddressDto ShippingAddress;

    /*public List<OrderItemDto> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        OrderItems = orderItems;
    }*/

    public AddressDto getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        ShippingAddress = shippingAddress;
    }
}

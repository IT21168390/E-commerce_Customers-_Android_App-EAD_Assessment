package com.example.e_commercecustomers_ead.api_models;

import java.io.Serializable;

public class UpdateOrderModel implements Serializable {
    public AddressDto ShippingAddress;

    public AddressDto getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        ShippingAddress = shippingAddress;
    }
}

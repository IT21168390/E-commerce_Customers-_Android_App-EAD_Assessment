package com.example.e_commercecustomers_ead.api_models;

public class Address {
    public String Street;
    public String City;
    public String ZipCode;

    public Address() {
    }

    public Address(String street, String city, String zipCode) {
        Street = street;
        City = city;
        ZipCode = zipCode;
    }

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

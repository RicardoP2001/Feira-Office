package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressResponse_Billing {
    @JsonProperty("BillingAddress1")
    public String address1;
    @JsonProperty("BillingAddress2")
    public String address2;
    @JsonProperty("BillingPostalCode")
    public String postalCode;
    @JsonProperty("BillingCity")
    public String city;
    @JsonProperty("BillingCountry")
    public String country;

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

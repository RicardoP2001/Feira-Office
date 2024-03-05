package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class APIResponseOnlyOneProducts {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("Product")
    private List<Products> product;

    APIResponseOnlyOneProducts(){}

    public APIResponseOnlyOneProducts(String status, List<Products> product) {
        this.status = status;
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Products> getProduct() {
        return product;
    }

    public void setProduct(List<Products> product) {
        this.product = product;
    }
}

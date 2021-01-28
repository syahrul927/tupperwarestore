package com.example.tupperwarestore.model;

import java.util.List;

public class CheckoutRequest {
    private List<String> products;

    public CheckoutRequest() {
    }

    public CheckoutRequest(List<String> products) {
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }
}

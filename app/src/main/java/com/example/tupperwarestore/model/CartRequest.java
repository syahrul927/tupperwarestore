package com.example.tupperwarestore.model;

public class CartRequest {
    private String productId;
    private Integer qty;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public CartRequest(String productId, Integer qty) {
        this.productId = productId;
        this.qty = qty;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}

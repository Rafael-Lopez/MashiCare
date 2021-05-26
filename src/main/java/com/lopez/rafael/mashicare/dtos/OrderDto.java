package com.lopez.rafael.mashicare.dtos;

import java.util.List;

public class OrderDto {
    private List<ProductOrderDto> products;
    private String username;

    public List<ProductOrderDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderDto> products) {
        this.products = products;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

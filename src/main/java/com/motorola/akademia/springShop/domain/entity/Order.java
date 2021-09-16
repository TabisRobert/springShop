package com.motorola.akademia.springShop.domain.entity;

import java.util.ArrayList;

public class Order {
    private final String orderId;
    private final ArrayList<Product> products;

    public Order(String orderId, ArrayList<Product> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public String getOrderId() {
        return orderId;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}

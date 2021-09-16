package com.motorola.akademia.springShop.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {
    private final Integer orderId;
    private final ArrayList<Cart.Article> products;
    private BigDecimal orderValue;

    public Order(Integer orderId, ArrayList<Cart.Article> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ArrayList<Cart.Article> getProducts() {
        return products;
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }
}

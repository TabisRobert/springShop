package com.motorola.akademia.springShop.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {
    private final ArrayList<Article> articles;
    private boolean isSpecialOfferApplied = false;
    private BigDecimal totalCartValue;

    public Cart(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public boolean isSpecialOfferApplied() {
        return isSpecialOfferApplied;
    }

    public void setSpecialOfferApplied(boolean specialOfferApplied) {
        isSpecialOfferApplied = specialOfferApplied;
    }

    public BigDecimal getTotalCartValue() {
        return totalCartValue;
    }

    public void setTotalCartValue(BigDecimal totalCartValue) {
        this.totalCartValue = totalCartValue;
    }

    public static class Article{
        private final Product product;
        private BigDecimal quantity;
        private BigDecimal totalPrice;

        public Article(Product product, BigDecimal quantity, BigDecimal totalPrice) {
            this.product = product;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Product getProduct() {
            return product;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }
    }
}

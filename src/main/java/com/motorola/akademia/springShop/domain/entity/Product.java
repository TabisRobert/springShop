package com.motorola.akademia.springShop.domain.entity;

import java.math.BigDecimal;

public class Product {
    private String name;
    private String description;
    private BigDecimal price;

    public Product(){}

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static final class ProductBuilder{
        private String name;
        private String description;
        private BigDecimal price;

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price){
            this.price = price;
            return this;
        }

        public Product build() {
            if(name.isEmpty()){
                throw new IllegalStateException("Name cannot be empty");
            }
            if(description.isEmpty()){
                throw new IllegalStateException("Description cannot be empty");
            }
            if(price == null){
                throw new IllegalStateException("Price cannot be empty");
            }

            Product product = new Product();
            product.name = this.name;
            product.description = this.description;
            product.price = this.price;

            return product;

        }
    }
}

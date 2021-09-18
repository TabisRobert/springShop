package com.motorola.akademia.springShop.repository;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.enums.ProductCategory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private ArrayList<Product> products = new ArrayList<>(){{
        add(new Product("Mydło", "Pieniące", new BigDecimal("25.00"),ProductCategory.CLEANING_SUPPLIES));
        add(new Product("Masło", "Się roztrzasło", new BigDecimal("99.99"), ProductCategory.FOOD));
        add(new Product("Chleb", "Razowy", new BigDecimal("5.50"), ProductCategory.FOOD));
        add(new Product("Krem", "Do czego tylko zechcesz ", new BigDecimal("10.00"), ProductCategory.COSMETICS));
    }};

    public List<Product> all() {
        return products;
    }

    public Product byName(String name) {
        for (Product product : products) {
            if (name.equals(product.getName())) {
                return product;
            }
        }
        return null;
    }

    public void addNewProduct(String name, String description, double price, ProductCategory productCategory){
        Product product = new Product.ProductBuilder()
                .name(name)
                .description(description)
                .price(getRoundedBigDecimalFromGivenNumber(price))
                .productCategory(productCategory)
                .build();
        products.add(product);
    }

    public void editGivenProduct(String oldProductName, String newProductName, String productDescription, double productPrice, ProductCategory productCategory) {
        for (Product product : products) {
            if (oldProductName.equals(product.getName())) {
                product.setName(newProductName);
                product.setDescription(productDescription);
                product.setPrice(getRoundedBigDecimalFromGivenNumber(productPrice));
                product.setProductCategory(productCategory);
            }
        }
    }

    BigDecimal getRoundedBigDecimalFromGivenNumber(double price) {
        BigDecimal productPrice = new BigDecimal(price, MathContext.DECIMAL64);
        productPrice = productPrice.setScale(2, RoundingMode.HALF_UP);
        return productPrice;
    }

    public void deleteProduct(String productName) {
        products.remove(byName(productName));
    }


    public List<Product> getProductsByCategory(ProductCategory productCategory) {
        List<Product> categorizedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductCategory()==productCategory){
                categorizedProducts.add(product);
            }
        }
        return categorizedProducts;
    }

    public BigDecimal getProductPrice(Product product) {
        return product.getPrice();
    }
}

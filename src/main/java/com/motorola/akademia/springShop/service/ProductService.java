package com.motorola.akademia.springShop.service;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product showProductDetails(String name) {
        return productRepository.byName(name);
    }

    public List<Product> showAllProducts() {
        return productRepository.all();
    }

    public void createNewProduct(String name, String description, double price, ProductCategory productCategory){
        productRepository.addNewProduct(name, description, price, productCategory);
    }

    public void editProductWithGivenName(String oldProductName, String newProductName, String productDescription, double productPrice, ProductCategory productCategory) {
        productRepository.editGivenProduct(oldProductName, newProductName, productDescription, productPrice, productCategory);
    }

    public void deleteGivenProduct(String productName) {
        productRepository.deleteProduct(productName);
    }


    public List<Product> getAllProductsFromCategory(ProductCategory productCategory) {
        return productRepository.getProductsByCategory(productCategory);
    }
}

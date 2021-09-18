package com.motorola.akademia.springShop.repository;

import com.motorola.akademia.springShop.domain.enums.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    public ProductCategory[] getArrayOfCategories() {
        return ProductCategory.values();
    }
}

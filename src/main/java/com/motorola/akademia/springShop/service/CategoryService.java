package com.motorola.akademia.springShop.service;

import com.motorola.akademia.springShop.domain.enums.ProductCategory;
import com.motorola.akademia.springShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductCategory[] getAllCategories() {
        return categoryRepository.getArrayOfCategories();
    }
}

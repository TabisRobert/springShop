package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.service.CategoryService;
import com.motorola.akademia.springShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/user")
    public String getAllProducts(Model model){
        List<Product> productList = productService.showAllProducts();
        ProductCategory[] categories = categoryService.getAllCategories();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categories);
        return "user_view";
    }
}

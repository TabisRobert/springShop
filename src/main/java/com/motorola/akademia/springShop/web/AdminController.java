package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.service.CategoryService;
import com.motorola.akademia.springShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin")
    public String openAdministratorView(Model model){
        List<Product> productList = productService.showAllProducts();
        ProductCategory[] categories = categoryService.getAllCategories();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categories);
        return "admin_view";
    }

    @PostMapping("/product_edition")
    public String openProductEditor(@RequestParam("product_name") String name, Model model){
        Product product = productService.showProductDetails(name);
        ProductCategory[] categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "editor";
    }
}

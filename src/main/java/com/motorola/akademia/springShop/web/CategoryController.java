package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.service.CategoryService;
import com.motorola.akademia.springShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/product_categories")
    public String getAllCategories(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = (List<Product>)session.getAttribute("categorized_products");
        ProductCategory[] categories = categoryService.getAllCategories();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categories);
        return "category";
    }
    @PostMapping("/list_of_products")
    public String showProductsFromGivenCategory(@RequestParam("product_category") ProductCategory productCategory, HttpServletRequest request){
        List<Product> productList = productService.getAllProductsFromCategory(productCategory);
        HttpSession session = request.getSession();
        session.setAttribute("categorized_products", productList);
        return "redirect:/product_categories";
    }


}

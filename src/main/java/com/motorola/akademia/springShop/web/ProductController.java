package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.service.CartService;
import com.motorola.akademia.springShop.service.CategoryService;
import com.motorola.akademia.springShop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{name}")
    public String showProductDetails(@PathVariable String name, Model model){
        Product product = productService.showProductDetails(name);
        model.addAttribute("product", product);
        return "details";
    }

    @PostMapping("/add_product")
    public String addNewProduct(@RequestParam("name") String productName, @RequestParam("desc") String productDescription, @RequestParam("price") double productPrice, @RequestParam("product_category") ProductCategory productCategory ){
        productService.createNewProduct(productName, productDescription, productPrice, productCategory);
        return "redirect:/admin";
    }

    @PostMapping("/edit_product")
    public String editExistingProduct(@RequestParam("old_product_name") String oldProductName,@RequestParam("name") String newProductName, @RequestParam("desc") String productDescription, @RequestParam("price") double productPrice, @RequestParam("product_category") ProductCategory productCategory ) {
        productService.editProductWithGivenName(oldProductName, newProductName, productDescription, productPrice, productCategory);
        return "redirect:/admin";
    }

    @PostMapping("/delete_product")
    public String deleteProduct(@RequestParam("name") String productName){
        productService.deleteGivenProduct(productName);
        return "redirect:/admin";
    }
}

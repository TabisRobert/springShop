package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
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

    @GetMapping("/")
    String getAllProducts(Model model){
        List<Product> productList = productService.showAllProducts();
        ProductCategory[] categories = productService.getAllCategories();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categories);
        return "index";
    }
    @GetMapping("/admin")
    String openAdministratorView(Model model){
        List<Product> productList = productService.showAllProducts();
        ProductCategory[] categories = productService.getAllCategories();
        model.addAttribute("products", productList);
        model.addAttribute("categories", categories);
        return "admin_view";
    }

    @GetMapping("/{name}")
    String productsDetails(@PathVariable String name, Model model){
        Product product = productService.showProductDetails(name);
        model.addAttribute("product", product);
        return "details";
    }

    @PostMapping("/addproduct")
    public String addNewProduct(@RequestParam("name") String productName, @RequestParam("desc") String productDescription, @RequestParam("price") double productPrice, @RequestParam("product_category") ProductCategory productCategory ){
        productService.createNewProduct(productName, productDescription, productPrice, productCategory);
        return "redirect:/admin";
    }

    @PostMapping("/productedition")
    String openProductEditor(@RequestParam("product_name") String name, Model model){
        Product product = productService.showProductDetails(name);
        ProductCategory[] categories = productService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "editor";
    }

    @PostMapping("/editproduct")
    public String editExistingProduct(@RequestParam("old_product_name") String oldProductName,@RequestParam("name") String newProductName, @RequestParam("desc") String productDescription, @RequestParam("price") double productPrice, @RequestParam("product_category") ProductCategory productCategory ) {
        productService.editProductWithGivenName(oldProductName, newProductName, productDescription, productPrice, productCategory);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("name") String productName){
        productService.deleteGivenProduct(productName);
        return "redirect:/admin";
    }
}

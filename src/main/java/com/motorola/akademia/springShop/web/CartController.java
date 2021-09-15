package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add_to_cart")
    public String addProductToCart(@RequestParam("product_name") String name, @RequestParam("quantity") double quantity, HttpServletRequest request) {
        HttpSession session = getHttpSession(request);
        if (session.getAttribute("existing_cart") == null) {
            Cart cart = cartService.createNewCart();
            session.setAttribute("existing_cart", cart);
            cartService.addArticleToCart(cart, name, quantity);
        } else {
            cartService.addArticleToCart((Cart) session.getAttribute("existing_cart"), name, quantity);
        }
        return "redirect:/user";
    }

    @GetMapping("/cart")
    public String getCartContent(Model model, HttpServletRequest request) {
        HttpSession session = getHttpSession(request);
        if (session.getAttribute("existing_cart") != null) {
            Cart cart = (Cart) session.getAttribute("existing_cart");
            ArrayList<Cart.Article> articlesList = cartService.getAllArticlesFromCart(cart);
            model.addAttribute("articles", articlesList);
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("articles", null);
        }
        return "cart_content";
    }


    @PostMapping("/edit_quantity")
    public String editQuantityOfGivenArticle(@RequestParam("article_quantity") double newQuantity, @RequestParam("edited_article") String name, HttpServletRequest request) {
        HttpSession session = getHttpSession(request);
        cartService.editQuantityOfArticleInCart((Cart)session.getAttribute("existing_cart"), name, newQuantity);
        return "redirect:/cart";
    }

    @PostMapping("/delete_article")
    public String deleteGivenArticle(@RequestParam("deleted_article") String name, HttpServletRequest request){
        HttpSession session = getHttpSession(request);
        cartService.deleteArticle((Cart)session.getAttribute("existing_cart"), name);
        return "redirect:/cart";
    }

    private HttpSession getHttpSession(HttpServletRequest request) {
        return request.getSession();
    }
}

package com.motorola.akademia.springShop.service;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public void addArticleToCart(Cart cart, String name, double quantity) {
        cartRepository.putArticleIntoCart(cart, name, quantity);
    }

    public Cart createNewCart() {
        return cartRepository.createCart();
    }

    public ArrayList<Cart.Article> getAllArticlesFromCart(Cart cart){
        return cartRepository.articles(cart);
    }

    public void editQuantityOfArticleInCart(Cart cart, String article, double newQuantity) {
        cartRepository.editQuantityOfGivenArticle(cart, article, newQuantity);
    }

    public void deleteArticle(Cart existing_cart, String name) {
        cartRepository.deleteArticleFromCart(existing_cart, getArticleByName(existing_cart, name));
    }

    private Cart.Article getArticleByName(Cart cart, String name) {
        return cartRepository.byName(cart, name);
    }
}

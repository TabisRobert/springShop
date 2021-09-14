package com.motorola.akademia.springShop.repository;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;

@Repository
public class CartRepository {

    @Autowired
    private ProductRepository productRepository;

    public Cart createCart() {
        ArrayList<Cart.Article> articles = new ArrayList<>();
        return new Cart(articles);
    }

    private Cart.Article createArticle(String name, double quantity) {

        Product product = productRepository.byName(name);
        BigDecimal articleQuantity = getBigDecimal(quantity);
        BigDecimal totalPrice = productRepository.getProductPrice(product).multiply(articleQuantity);
        return new Cart.Article(product, articleQuantity, totalPrice);
    }

    public void putArticleIntoCart(Cart cart, String name, double quantity) {
        if (byName(cart, name) == null) {
            cart.getArticles().add(createArticle(name, quantity));
        } else {
            BigDecimal newQuantity = getBigDecimal(quantity).add(byName(cart, name).getQuantity());
            byName(cart, name).setQuantity(newQuantity);
            byName(cart, name).setTotalPrice(productRepository.byName(name).getPrice().multiply(newQuantity));
        }
    }

    public ArrayList<Cart.Article> articles(Cart cart) {
        return cart.getArticles();
    }

    public void editQuantityOfGivenArticle(Cart cart, String articleName, double newQuantity) {
        Cart.Article article = byName(cart, articleName);
        if (newQuantity==0){
            deleteArticleFromCart(cart, article);
        }

        article.setQuantity(getBigDecimal(newQuantity));
        article.setTotalPrice(article.getProduct().getPrice().multiply(article.getQuantity()));
    }

    private BigDecimal getBigDecimal(double value) {
        return productRepository.getRoundedBigDecimalFromGivenNumber(value);
    }

    public Cart.Article byName(Cart cart, String name) {
        for (Cart.Article article : articles(cart)) {
            if (name.equals(article.getProduct().getName())) {
                return article;
            }
        }
        return null;
    }

    public void deleteArticleFromCart(Cart cart, Cart.Article article){
        cart.getArticles().remove(article);
    }
}

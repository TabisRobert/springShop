package com.motorola.akademia.springShop.repository;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.domain.entity.Product;
import com.motorola.akademia.springShop.domain.entity.ProductCategory;
import com.motorola.akademia.springShop.domain.entity.SpecialOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CartRepository {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
            updateCartTotalValue(cart);
            checkIfSpecialOffersAreAvaialable(cart);
        } else {
            BigDecimal newQuantity = getBigDecimal(quantity).add(byName(cart, name).getQuantity());
            byName(cart, name).setQuantity(newQuantity);
            byName(cart, name).setTotalPrice(productRepository.byName(name).getPrice().multiply(newQuantity));
            updateCartTotalValue(cart);
            checkIfSpecialOffersAreAvaialable(cart);
        }
    }


    public ArrayList<Cart.Article> articles(Cart cart) {
        return cart.getArticles();
    }

    public void editQuantityOfGivenArticle(Cart cart, String articleName, double newQuantity) {
        Cart.Article article = byName(cart, articleName);
        if (newQuantity == 0) {
            deleteArticleFromCart(cart, article);
        }
        article.setQuantity(getBigDecimal(newQuantity));
        article.setTotalPrice(article.getProduct().getPrice().multiply(article.getQuantity()));
        updateCartTotalValue(cart);
        checkIfSpecialOffersAreAvaialable(cart);
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

    public void deleteArticleFromCart(Cart cart, Cart.Article article) {
        removeArticle(cart, article);
        checkIfSpecialOffersAreAvaialable(cart);
        updateCartTotalValue(cart);
    }

    private void removeArticle(Cart cart, Cart.Article article) {
        cart.getArticles().remove(article);
    }

    private void updateCartTotalValue(Cart cart) {
        cart.setTotalCartValue(calculateTotalPriceOfProductsInCart(cart));
    }

    private BigDecimal calculateTotalPriceOfProductsInCart(Cart cart) {
        BigDecimal sum = new BigDecimal(0);
        if (cart.getArticles().isEmpty()) {
            cart.setSpecialOfferApplied(false);
            return sum;
        }
        for (Cart.Article article : cart.getArticles()) {
            sum = sum.add(article.getTotalPrice());
        }
        return sum;
    }

    private void checkIfSpecialOffersAreAvaialable(Cart cart) {
        if (!cart.isSpecialOfferApplied() || cart.getSpecialOffer() == SpecialOffer.TEN_FOODS_DISCOUNT) {
            discountForTenFoodArticles(cart);
        }
        if (!cart.isSpecialOfferApplied() || cart.getSpecialOffer() == SpecialOffer.EACH_CATEGORY_DISCOUNT) {
            discountForProductsDiversity(cart);
        }
        if (!cart.isSpecialOfferApplied() || cart.getSpecialOffer() == SpecialOffer.ONE_FOR_FREE) {
            oneCosmeticIsFreee(cart);
        }
    }

    private void discountForTenFoodArticles(Cart cart) {
        int foodProductsQuantity = 0;
        BigDecimal discount = productRepository.getRoundedBigDecimalFromGivenNumber(0.95);
        for (Cart.Article article : cart.getArticles()) {
            if (article.getProduct().getProductCategory() == ProductCategory.FOOD) {
                foodProductsQuantity += article.getQuantity().intValue();
            }
        }
        if (foodProductsQuantity >= 10) {
            applyDiscount(cart, discount);
            cart.setSpecialOffer(SpecialOffer.TEN_FOODS_DISCOUNT);
            cart.setSpecialOfferApplied(true);
        } else {
            cart.setSpecialOfferApplied(false);
        }
    }

    private void applyDiscount(Cart cart, BigDecimal discount) {
        for (Cart.Article article : cart.getArticles()) {
            article.setTotalPrice(article.getProduct().getPrice().multiply(article.getQuantity()).multiply(discount));
        }
        updateCartTotalValue(cart);
    }

    private void discountForProductsDiversity(Cart cart) {
        BigDecimal discount = productRepository.getRoundedBigDecimalFromGivenNumber(0.97);
        List<ProductCategory> categoriesInCart = new ArrayList<>();
        for (Cart.Article article : cart.getArticles()) {
            categoriesInCart.add(article.getProduct().getProductCategory());
        }
        if (categoriesInCart.containsAll(Arrays.asList(categoryRepository.getArrayOfCategories()))) {
            applyDiscount(cart, discount);
            cart.setSpecialOffer(SpecialOffer.EACH_CATEGORY_DISCOUNT);
            cart.setSpecialOfferApplied(true);
        }
        if (!categoriesInCart.containsAll(Arrays.asList(categoryRepository.getArrayOfCategories()))) {
            applyDiscount(cart, BigDecimal.ONE);
            cart.setSpecialOfferApplied(false);
        }

    }

    private void oneCosmeticIsFreee(Cart cart) {
        boolean newOffer = false;
        for (Cart.Article article : cart.getArticles()) {
            if (article.getProduct().getProductCategory() == ProductCategory.COSMETICS && article.getQuantity().intValue() >= 3) {
                article.setTotalPrice(article.getTotalPrice().subtract(article.getProduct().getPrice()));
                newOffer = true;
            }
        }
        if (newOffer) {
            updateCartTotalValue(cart);
            cart.setSpecialOfferApplied(true);
            cart.setSpecialOffer(SpecialOffer.ONE_FOR_FREE);
        } else {
            cart.setSpecialOfferApplied(false);
        }
    }

    void emptyCart(Cart cart) {
        cart.getArticles().clear();
        cart.setSpecialOfferApplied(false);
        cart.setSpecialOffer(null);
        cart.setTotalCartValue(BigDecimal.ZERO);
    }
}


package com.motorola.akademia.springShop.repository;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.domain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class OrderRepository {

    @Autowired
    private CartRepository cartRepository;

    private Integer orderId = 0;
    private ArrayList<Order> listOfOrders = new ArrayList<>();

    public void createOrder(Cart cart) {
        ArrayList<Cart.Article> products = new ArrayList<>(cart.getArticles());
        Order order = new Order(orderId++, products);
        order.setOrderValue(cart.getTotalCartValue());
        listOfOrders.add(order);
        cartRepository.emptyCart(cart);
    }

    public ArrayList<Order> getListOfOrders() {
        return listOfOrders;
    }

    public void deleteOrder(Integer id) {
        listOfOrders.remove(getOrderById(id));
    }

    private Order getOrderById(Integer id) {
        for (Order order : listOfOrders) {
            if (order.getOrderId().equals(id)) {
                return order;
            }
        }
        return null;
    }

}

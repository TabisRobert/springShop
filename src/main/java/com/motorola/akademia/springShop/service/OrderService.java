package com.motorola.akademia.springShop.service;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.domain.entity.Order;
import com.motorola.akademia.springShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public ArrayList<Order> getAllOrders() {
        return orderRepository.getListOfOrders();
    }

    public void createOrderFromCart(Cart cart) {
        orderRepository.createOrder(cart);
    }

    public void deleteOrderByGivenId(Integer id) {
        orderRepository.deleteOrder(id);
    }
}

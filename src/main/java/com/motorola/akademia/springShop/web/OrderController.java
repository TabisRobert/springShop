package com.motorola.akademia.springShop.web;

import com.motorola.akademia.springShop.domain.entity.Cart;
import com.motorola.akademia.springShop.domain.entity.Order;
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
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/show_orders")
    public String showOrders(Model model){
        ArrayList<Order> listOfOrders = orderService.getAllOrders();
        model.addAttribute("orders", listOfOrders);
        return "order_list";
    }

    @PostMapping("/create_order")
    public String createNewOrder(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session.getAttribute("existing_cart") != null){
            Cart cart = (Cart) session.getAttribute("existing_cart");
            orderService.createOrderFromCart(cart);
        }
        return "redirect:/cart_content";
    }
    @PostMapping("/delete_order")
    public String deleteOrderById(@RequestParam("deleted_order") Integer id){
        orderService.deleteOrderByGivenId(id);
        return "redirect:/show_orders";
    }
}

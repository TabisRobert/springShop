package com.motorola.akademia.springShop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/home")
    public String openHomePage(){
        return "home_page";
    }

    @GetMapping("/special_offers")
    public String showSpecialOffers(){
        return "spec_offers";
    }
}

package com.perficient.shoppingcart.infrastructure.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// https://www.getambassador.io/blog/7-rest-api-design-best-practices
@RestController()
@RequestMapping("api/shoppingcar")
public class ShoppingCarController {
    @PostMapping("/addItem")
    public String addItem() {
        return "Add Item";
    }

    @DeleteMapping("/removeItem{id}")
    public String removeItem() {
        return "Remove Item";
    }

    @GetMapping("/getItems")
    public String viewItems() {
        return "View Items";
    }

}

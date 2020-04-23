package com.comsumer.controller;

import com.comsumer.feignClient.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderApi orderApi;

    @GetMapping(value = "/order")
    public String order() {
        return orderApi.order();
    }
}


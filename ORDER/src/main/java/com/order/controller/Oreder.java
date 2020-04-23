package com.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oreder {



    @GetMapping("/order")
    public  String order(){
        return  "120 orders";
    }
}

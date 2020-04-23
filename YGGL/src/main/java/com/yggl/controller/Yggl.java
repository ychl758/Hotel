package com.yggl.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Yggl {

    @Value("${server.port}")
    private int port;


    @GetMapping("/yggl")
    public  String yggl(){
        return  "yggl"+port;
    }
}

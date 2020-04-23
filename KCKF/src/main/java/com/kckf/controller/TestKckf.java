package com.kckf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestKckf {


    @GetMapping("/kckf")
    public  String kckf(){
        return  "kckf 121 jian";
    }
}

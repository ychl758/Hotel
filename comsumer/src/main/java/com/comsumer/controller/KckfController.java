package com.comsumer.controller;

import com.comsumer.feignClient.KckfApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KckfController {
    @Autowired
    KckfApi kckfApi;

    @GetMapping("/kckf")
    public  String kckf(){
        return  kckfApi.kckf();
    }
}

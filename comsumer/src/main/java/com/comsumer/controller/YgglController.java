package com.comsumer.controller;

import com.comsumer.feignClient.YgglApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YgglController {
    @Autowired
    YgglApi ygglApi;


    @GetMapping("/yggl")
   public String yggl(){
        return  ygglApi.yggl();
    }

}

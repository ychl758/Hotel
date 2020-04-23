package com.comsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LunxunController {


    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @RequestMapping("/lunxuntest")
    public String lunuxntest() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("ycl-yggl");
        System.out.println(serviceInstance.getHost()+":"+serviceInstance.getPort());
        return "1";
    }
}

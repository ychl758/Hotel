package com.comsumer.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ycl-order")
public interface OrderApi {

    @GetMapping(value = "/order")
      String order();
}

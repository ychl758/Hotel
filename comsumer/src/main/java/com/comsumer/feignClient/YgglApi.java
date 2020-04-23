package com.comsumer.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ycl-yggl")
public interface YgglApi {

    @GetMapping("/yggl")
    String yggl();
}

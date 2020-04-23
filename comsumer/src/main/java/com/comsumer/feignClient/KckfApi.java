package com.comsumer.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ycl-kckf")
public interface KckfApi {


    @GetMapping("/kckf")
    String kckf();
}

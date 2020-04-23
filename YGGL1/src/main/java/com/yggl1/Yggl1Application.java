package com.yggl1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Yggl1Application {

    public static void main(String[] args) {
        SpringApplication.run(Yggl1Application.class, args);
    }

}

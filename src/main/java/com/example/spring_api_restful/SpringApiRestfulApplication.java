package com.example.spring_api_restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringApiRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringApiRestfulApplication.class, args);
    }

}

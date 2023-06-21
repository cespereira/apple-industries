package com.apple.industries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class AppleIndustriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppleIndustriesApplication.class, args);
    }

}
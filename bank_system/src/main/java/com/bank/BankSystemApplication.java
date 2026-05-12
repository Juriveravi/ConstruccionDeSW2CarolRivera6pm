package com.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bank.infrastructure.persistence.jpa")
@EnableScheduling
public class BankSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankSystemApplication.class, args);
    }
}
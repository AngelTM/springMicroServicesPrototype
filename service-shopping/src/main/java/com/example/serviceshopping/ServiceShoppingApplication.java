package com.example.serviceshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceShoppingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceShoppingApplication.class, args);
	}

}

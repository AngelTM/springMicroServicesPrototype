package com.example.serviceshopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.serviceshopping.model.Customer;

@FeignClient(name = "service-customer", path = "/customers",fallback = CustomerHystrixFallbackFactory.class)
public interface CustomerClient {
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id);
}

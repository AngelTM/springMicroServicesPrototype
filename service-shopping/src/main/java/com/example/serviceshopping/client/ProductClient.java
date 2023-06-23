package com.example.serviceshopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.serviceshopping.model.Product;

@FeignClient(name = "service-product", path = "/api/product")
public interface ProductClient {

    @GetMapping(value = "/{ID}")
    public ResponseEntity<Product> getProduct(@PathVariable("ID") Long productID);
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity);
}

package com.store.product.serviceproduct.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.product.serviceproduct.entity.Category;
import com.store.product.serviceproduct.entity.Product;
import com.store.product.serviceproduct.service.ProductService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @GetMapping()
    public ResponseEntity<List<Product>> getProducts(@RequestParam(value = "categoryID",required = false) Long categoryID){
        List<Product> products;
        if (null == categoryID) {
           products = productService.listAllProduct();
        }else{
            products = productService.findByCategory(Category.builder().id(categoryID).build());
        }
        if(products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
          
        return ResponseEntity.ok(products);
    }
    @GetMapping(value = "/{ID}")
    public ResponseEntity<Product> getProduct(@PathVariable("ID") Long productID){
        Optional<Product> product = productService.getProduct(productID);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product,BindingResult result){
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }
    
    @PutMapping(value = "/{ID}")
    public ResponseEntity<Product> updateProduct(@PathVariable("ID") Long id,@RequestBody Product product){
        Product productUpdated = productService.updateProduct(product);
        if (null == productUpdated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productUpdated);
    }

    @DeleteMapping
    public ResponseEntity<Product> deleteProduct(@PathVariable("ID") Long productID){
        Product productdeleted = productService.deleteProduct(productID);
        if (null == productdeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productdeleted);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable  Long id ,@RequestParam(name = "quantity", required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
        .map(err->{
            Map<String,String> error = new HashMap<>();
            error.put(err.getField(), err.getDefaultMessage());
            return error;
            }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}

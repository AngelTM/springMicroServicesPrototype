package com.store.product.serviceproduct.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.store.product.serviceproduct.entity.Category;
import com.store.product.serviceproduct.entity.Product;


public interface ProductService {
    public List<Product> listAllProduct();
    public Optional<Product> getProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product deleteProduct(Long id);
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double quantity);
}

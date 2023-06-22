package com.store.product.serviceproduct.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.product.serviceproduct.entity.Category;
import com.store.product.serviceproduct.entity.Product;
import com.store.product.serviceproduct.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Autowired
    private  ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll(); 
    }

    @Override
    public Optional<Product> getProduct(Long id) {
       return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
       product.setStatus("CREATED");
       product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productdb = getProduct(product.getId());
        if (productdb.isPresent()) {
        productdb.get().setName(product.getName());
        productdb.get().setDescription(product.getDescription());
        productdb.get().setCategory(product.getCategory());
        productdb.get().setPrice(product.getPrice());
        return productRepository.save(productdb.get());
        }
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        Optional<Product> productdb = getProduct(id);
        if (productdb.isPresent()) {
            productdb.get().setStatus("DELETED");
            return productRepository.save(productdb.get());
        }
        return null;
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Optional<Product> productdb = getProduct(id);
        if (productdb.isPresent()) {
            Double stock = productdb.get().getStock() +quantity;
            productdb.get().setStock(stock);
            return productRepository.save(productdb.get());
        }
        return null;
    }
    
}

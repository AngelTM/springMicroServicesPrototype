package com.store.product.serviceproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.product.serviceproduct.entity.Product;
import java.util.List;
import com.store.product.serviceproduct.entity.Category;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    public  List<Product> findByCategory(Category category);
}

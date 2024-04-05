package com.example.demo.repository;

import com.example.demo.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods if needed
//    Optional<Product> findByName(String name);
//    void deleteByName(String name);

}
package com.example.demo.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, String> {
}
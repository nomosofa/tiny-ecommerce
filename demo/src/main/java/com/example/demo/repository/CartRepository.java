package com.example.demo.repository;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 揭程
 * @version 1.0
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
}
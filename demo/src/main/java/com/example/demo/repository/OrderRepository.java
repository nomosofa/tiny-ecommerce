package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 揭程
 * @version 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
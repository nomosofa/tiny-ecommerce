package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 揭程
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
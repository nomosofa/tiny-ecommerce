package com.example.demo.repository;

import com.example.demo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
//    Optional<Cart> findByUser_UserIDAndProduct_ProductID(Long userId, Long productId);
//    List<Cart> findAllByUser_UserID(Long userId);
//    void deleteByUser_UserIDAndProduct_ProductID(Long userId, Long productId);
//
//    // 使用user的userID作为方法的参数名，让Spring Data JPA理解这个属性的路径
//    void deleteAllByUser_UserID(Long userId);
}
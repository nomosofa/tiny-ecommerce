package com.example.demo.repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find a cart item by user and product
    Optional<Cart> findByUserAndProductname(User user, String productname);

    // Find all cart items for a user
    List<Cart> findAllByUser(User user);

    // Delete a cart item by user and product
    void deleteByUserAndProductname(User user, String productname);

    // Delete all cart items for a user
    void deleteAllByUser(User user);
    @Query("SELECT c FROM Cart c WHERE c.user = :user")
    Page<Cart> findAllByUserWithPagination(User user, Pageable pageable);
}
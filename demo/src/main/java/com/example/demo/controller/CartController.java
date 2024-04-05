package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CartDTO;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 揭程
 * @version 1.0
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartDTO cartItemDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(cartItemDTO.getUserId(), cartItemDTO.getProductId(), cartItemDTO.getQuantity()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody CartDTO cartItemDTO) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemDTO.getUserId(), cartItemDTO.getProductId(), cartItemDTO.getQuantity()));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestBody CartDTO cartItemDTO) {
        return ResponseEntity.ok(cartService.removeItemFromCart(cartItemDTO.getUserId(), cartItemDTO.getProductId()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<ApiResponse> clearUserCart(@PathVariable Long userId) {
        ApiResponse response = cartService.clearUserCart(userId);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

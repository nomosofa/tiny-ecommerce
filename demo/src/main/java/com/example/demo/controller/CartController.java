package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CartDTO;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 揭程
 * @version 1.0
 */
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody CartDTO cartDTO) {
        ApiResponse response = cartService.addItemToCart(cartDTO);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    // Other endpoints...
    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable String username) {
        ApiResponse response = cartService.getUserCart(username);
        if (response.getData() == null) {
            return ResponseEntity.ok(new ApiResponse(false, "Cart is empty."));
        }
        return ResponseEntity.ok(new ApiResponse(true, "Cart retrieved successfully.", response.getData()));
    }

    @DeleteMapping("/{username}/{productname}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String username, @PathVariable String productname) {
        ApiResponse response = cartService.removeItemFromCart(username, productname);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/clear/{username}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String username) {
        ApiResponse response = cartService.clearCart(username);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

}
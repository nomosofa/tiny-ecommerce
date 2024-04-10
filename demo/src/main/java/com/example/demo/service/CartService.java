package com.example.demo.service;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CartDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository; // Assumes the existence of CartRepository

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public ApiResponse addItemToCart(Long userId, Long productId, Integer quantity) {
        throw new UnsupportedOperationException("The addItemToCart() method has not been implemented yet.");
//        // Verify that the user and product exist
//        if (!userRepository.existsById(userId) || !productRepository.existsById(productId)) {
//            return new ApiResponse(false, "User or Product not found.");
//        }
//        // 根据name判断存在哪个ds中
//        // 再去对应的ds中检查是否存在该product
//
//
//        // Check if the item already exists in the cart
//        Optional<Cart> existingCart = cartRepository.findByUser_UserIDAndProduct_ProductID(userId, productId);
//        if (existingCart.isPresent()) {
//            // Update the quantity if it exists
//            Cart cart = existingCart.get();
//            cart.setQuantity(cart.getQuantity() + quantity);
//            cartRepository.save(cart);
//            return new ApiResponse(true, "Item quantity updated in cart.");
//        } else {
//            // Add a new item to the cart
//            Cart newItem = new Cart();
//            newItem.setUser(new User(userId)); // Assume User class has a constructor accepting userId
//            newItem.setProduct(new Product(productId)); // Assume Product class has a constructor accepting productId
//            newItem.setQuantity(quantity);
//            cartRepository.save(newItem);
//            return new ApiResponse(true, "Item added to cart.");
//        }
    }

    public ApiResponse updateCartItem(Long userId, Long productId, Integer quantity) {
        throw new UnsupportedOperationException("The updateCartItem() method has not been implemented yet.");
//        // Check if the cart item exists
//        Optional<Cart> cartOpt = cartRepository.findByUser_UserIDAndProduct_ProductID(userId, productId);
//        if (!cartOpt.isPresent()) {
//            return new ApiResponse(false, "Cart item not found.");
//        }
//
//        // Update the item's quantity
//        Cart cart = cartOpt.get();
//        cart.setQuantity(quantity);
//        cartRepository.save(cart);
//        return new ApiResponse(true, "Cart item quantity updated.");
    }

    public ApiResponse removeItemFromCart(Long userId, Long productId) {
        throw new UnsupportedOperationException("The removeItemFromCart() method has not been implemented yet.");
//        if (!cartRepository.findByUser_UserIDAndProduct_ProductID(userId, productId).isPresent()) {
//            return new ApiResponse(false, "Cart item not found.");
//        }
//        cartRepository.deleteByUser_UserIDAndProduct_ProductID(userId, productId);
//        return new ApiResponse(true, "Item removed from cart.");
    }

    public ApiResponse clearUserCart(Long userId) {
        throw new UnsupportedOperationException("The clearUserCart() method has not been implemented yet.");
//        if (!userRepository.existsById(userId)) {
//            return new ApiResponse(false, "User not found.");
//        }
//
//        cartRepository.deleteAllByUser_UserID(userId);
//        return new ApiResponse(true, "User cart cleared.");
    }

    public ApiResponse getUserCart(Long userId) {
        throw new UnsupportedOperationException("The getUserCart() method has not been implemented yet.");
//        if (!userRepository.existsById(userId)) {
//            return new ApiResponse(false, "User not found.");
//        }
//
//        List<Cart> carts = cartRepository.findAllByUser_UserID(userId);
//        List<CartDTO> cartDTOs = convertToCartDTOs(carts);
//        return new ApiResponse(true, "Cart retrieved successfully.", cartDTOs);
    }

    private List<CartDTO> convertToCartDTOs(List<Cart> carts) {
        throw new UnsupportedOperationException("The convertToCartDTOs() method has not been implemented yet.");
        // Conversion logic to convert entities to DTOs
//        return carts.stream()
//                .map(item -> new CartDTO(item.getUser().getUserID(), item.getProduct().getProductID(), item.getQuantity()))
//                .collect(Collectors.toList());
    }
}

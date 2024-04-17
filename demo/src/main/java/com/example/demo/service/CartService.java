package com.example.demo.service;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CartDTO;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public ApiResponse addItemToCart(CartDTO cartDTO) {
        // Check if user exists
        Optional<User> user = userRepository.findById(cartDTO.getUsername());
        if (user.isEmpty()) {
            return new ApiResponse(false, "User not found.");
        }

        // Check if product exists
//        Optional<Product> product = productRepository.findById(cartDTO.getProductname());
        Optional<Product> product = productService.findProductByName(cartDTO.getProductname());
        if (product.isEmpty()) {
            return new ApiResponse(false, "Product not found.");
        }

        // Check if item already exists in cart
        Optional<Cart> existingItem = cartRepository.findByUserAndProductname(user.get(), product.get().getName());
        if (existingItem.isPresent()) {
            Cart item = existingItem.get();
            // Update quantity if item already exists
            item.setQuantity(cartDTO.getQuantity());
            cartRepository.save(item);
            return new ApiResponse(true, "Quantity updated in cart.");
        } else {
            // Add new item to cart
            Cart newItem = new Cart();
            newItem.setUser(user.get());
            newItem.setProductname(product.get().getName());
            newItem.setQuantity(cartDTO.getQuantity());
            cartRepository.save(newItem);
            return new ApiResponse(true, "Item added to cart.");
        }
    }

    // Other methods...
    public ApiResponse getUserCart(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return new ApiResponse(false, "User not found.");
        }
        List<Cart> cartItems = cartRepository.findAllByUser(user.get());

        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Cart cart : cartItems) {
            String productName = cart.getProductname();
            Optional<Product> productOptional = productService.findProductByName(productName);
            ProductDTO productDTO = productOptional.map(p ->
                            new ProductDTO(p.getName(), p.getPrice(), p.getCategory(), p.getBrand()))
                    .orElse(null);

            CartDTO cartDTO = new CartDTO(cart.getUser().getUsername(), productName, cart.getQuantity(), productDTO);
            cartDTOList.add(cartDTO);
        }

        return new ApiResponse(true, "Get items", cartDTOList) ;
    }

    public ApiResponse removeItemFromCart(String username, String productname) {
        // Check if user exists
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return new ApiResponse(false, "User not found.");
        }

        // Check if product exists
        Optional<Product> product = productService.findProductByName(productname);
        if (product.isEmpty()) {
            return new ApiResponse(false, "Product not found.");
        }

        // Check if item exists in cart
        Optional<Cart> existingItem = cartRepository.findByUserAndProductname(user.get(), productname);
        if (existingItem.isEmpty()) {
            return new ApiResponse(false, "Item not found in cart.");
        }

        // Remove item from cart
        cartRepository.delete(existingItem.get());
        return new ApiResponse(true, "Item removed from cart.");
    }

    @Transactional
    public ApiResponse clearCart(String username) {
        // Check if user exists
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return new ApiResponse(false, "User not found.");
        }

        // Clear the user's cart
        cartRepository.deleteAllByUser(user.get());
        return new ApiResponse(true, "Cart cleared successfully.");
    }


    private CartDTO convertToDTO(Cart cart) {
        return new CartDTO(
                cart.getUser().getUsername(),
                cart.getProductname(),
                cart.getQuantity()
        );
    }
}

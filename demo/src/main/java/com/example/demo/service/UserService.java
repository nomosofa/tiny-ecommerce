package com.example.demo.service;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RegistrationResult;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse registerUser(User user) {
        if(userRepository.findById(user.getUsername()).isPresent()) {
            return new ApiResponse(false, "Username already exists.");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ApiResponse(false, "Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new ApiResponse(true, "User registered successfully.", savedUser);
    }

    public ApiResponse updateUser(String username, User updatedUser) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.setEmail(updatedUser.getEmail());
        // Update other fields as necessary
        User savedUser = userRepository.save(user);
        return new ApiResponse(true, "User updated successfully.", savedUser);
    }

    public ApiResponse getUserDetails(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        // You might want to return a DTO instead of the User entity
        return new ApiResponse(true, "User found.", user);
    }

//    @Transactional
//    public RegistrationResult registerUser(User user) {
//        // 检查用户名是否已存在
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            return new RegistrationResult(false, "Username already exists: " + user.getUsername());
//        }
//
//        // 加密密码
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        // 保存用户
//        userRepository.save(user);
//        return new RegistrationResult(true, "User registered successfully");
//    }
//
//
//    public Optional<User> authenticateUser(String username, String password) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
//            return user;
//        }
//        return Optional.empty();
//    }
//
//    public Optional<User> findUserById(Long userId) {
//        return userRepository.findById(userId);
//    }
//
//    @Transactional
//    public User updateUser(User user) {
//        // 先通过用户名找到现有用户
//        return userRepository.findByUsername(user.getUsername()).map(existingUser -> {
//            // 只更新邮箱字段
//            existingUser.setEmail(user.getEmail());
//
//            // 保存更新后的用户信息
//            return userRepository.save(existingUser);
//        }).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername()));
//
//    }
//
//    @Transactional
//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }
//
//
//    public void changePassword(Long userId, String newPassword) {
//        throw new NotImplementedException("changePassword not implemented");
//    }
}
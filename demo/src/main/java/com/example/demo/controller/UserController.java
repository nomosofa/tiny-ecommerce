package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.RegistrationResult;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
        RegistrationResult result = userService.registerUser(user);
        if (!result.isSuccess()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, result.getMessage()));
        }
        return ResponseEntity.ok(new ApiResponse(true, result.getMessage()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 以下是更多操作，比如登录、更新用户信息等，根据需要添加
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse> loginUser(@RequestBody Map<String, String> credentials) {
//        Optional<User> user = userService.authenticateUser(credentials.get("username"), credentials.get("password"));
//        if (user.isPresent()) {
//            return ResponseEntity.ok(new ApiResponse(true, "登录成功", /* 这里添加token或用户信息 */));
//        } else {
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "用户名或密码错误"));
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody Map<String, String> credentials) {
        Optional<User> user = userService.authenticateUser(credentials.get("username"), credentials.get("password"));
        if (user.isPresent()) {
            String token = jwtTokenUtil.generateToken(user.get().getUsername());
            return ResponseEntity.ok(new ApiResponse(true, "登录成功", token));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "用户名或密码错误"));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // 这里假设用户更新操作是完整的用户实体更新，实际应用中可能需要更细粒度的控制
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, Object> payload) {
        Long userId = Long.parseLong(payload.get("userId").toString());
        String newPassword = payload.get("newPassword").toString();
        userService.changePassword(userId, newPassword);
        return ResponseEntity.ok(Map.of("message", "密码更新成功"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(Map.of("message", "用户删除成功"));
    }
}
package com.example.demo.entity;

import com.example.demo.enumclass.Role;
import jakarta.persistence.*;

/**
 * @author 揭程
 * @version 1.0
 */
@Entity
@Table(name = "Users")
public class User {

    @Id
    private String username;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Constructors, Getters, Setters


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
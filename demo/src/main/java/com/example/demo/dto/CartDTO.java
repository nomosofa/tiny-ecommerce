package com.example.demo.dto;

/**
 * @author 揭程
 * @version 1.0
 */
public class CartDTO {
    private String username;
    private String productname;
    private Integer quantity;

    // Constructors, Getters, Setters


    public CartDTO() {
    }

    public CartDTO(String username, String productname, Integer quantity) {
        this.username = username;
        this.productname = productname;
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}


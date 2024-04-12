package com.example.demo.entity;

import jakarta.persistence.*;

/**
 * @author 揭程
 * @version 1.0
 */
@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "productname")
//    private Product product;
    private String productname;

    private Integer quantity;

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
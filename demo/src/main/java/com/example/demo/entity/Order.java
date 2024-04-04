package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

/**
 * @author 揭程
 * @version 1.0
 */
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    @Column(nullable = false)
    private Date datePlaced;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status; // 假设你有一个名为OrderStatus的枚举定义

    // getters 和 setters

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDatePlaced() {
        return datePlaced;
    }

    public void setDatePlaced(Date datePlaced) {
        this.datePlaced = datePlaced;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
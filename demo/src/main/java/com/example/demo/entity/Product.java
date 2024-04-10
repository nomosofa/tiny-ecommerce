package com.example.demo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author 揭程
 * @version 1.0
 */
@Entity
@Table(name = "Products")
public class Product {
    @Id
    private String name;

    private BigDecimal price;
    private String category;
    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

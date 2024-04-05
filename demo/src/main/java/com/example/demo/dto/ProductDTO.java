package com.example.demo.dto;

import java.math.BigDecimal;

/**
 * @author 揭程
 * @version 1.0
 */
public class ProductDTO {
    private String name;
    private BigDecimal price;
    private String category;
    private String brand;

    public ProductDTO(String name, BigDecimal price, String category, String brand) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

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

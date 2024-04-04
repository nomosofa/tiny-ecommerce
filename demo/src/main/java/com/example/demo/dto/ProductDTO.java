package com.example.demo.dto;

import java.math.BigDecimal;

/**
 * @author 揭程
 * @version 1.0
 */
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, BigDecimal price, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

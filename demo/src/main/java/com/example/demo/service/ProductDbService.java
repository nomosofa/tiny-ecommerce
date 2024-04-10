package com.example.demo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class ProductDbService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDbService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @DS("#dbIdentifier")
    public Product saveProduct(Product product, String dbIdentifier) {
        return productRepository.save(product);
    }

    @DS("#dbIdentifier")
    public Optional<Product> findProductByName(String name, String dbIdentifier) {
        return productRepository.findById(name);
    }

//    @DS("#dbIdentifier")
//    public void deleteProductByName(String name, String dbIdentifier) {
//        productRepository.deleteById(name);
//    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DS("#dbIdentifier")
    public List<Product> findAllProductsWithoutPage(String dbIdentifier) {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    @DS("#dbIdentifier")
    public Page<Product> findAllProducts(String dbIdentifier, Pageable pageable) {
        // 构建分页查询的SQL
        String sql = "SELECT * FROM products LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
        String countSql = "SELECT COUNT(*) FROM products";

        List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(products, pageable, count);
    }

    @DS("#dbIdentifier")
    public boolean deleteProductByName(String name, String dbIdentifier) {
        int updated = jdbcTemplate.update("DELETE FROM products WHERE name = ?", name);
        return updated > 0;
    }

    @DS("#dbIdentifier")
    public boolean updateProduct(ProductDTO productDTO, String dbIdentifier) {
        String sql = "UPDATE products SET price = ?, category = ?, brand = ? WHERE name = ?";
        int updated = jdbcTemplate.update(sql, productDTO.getPrice(), productDTO.getCategory(), productDTO.getBrand(), productDTO.getName());
        return updated > 0;
    }

    @DS("#dbIdentifier")
    public List<Product> searchProductsInDb(String nameLike, String category, String brand, BigDecimal minPrice, BigDecimal maxPrice, String dbIdentifier) {
        // 构建基础的SQL查询字符串
        String sql = "SELECT * FROM products WHERE 1=1";

        List<Object> params = new ArrayList<>();

        // 根据输入参数动态添加查询条件
        if (nameLike != null && !nameLike.isEmpty()) {
            sql += " AND name LIKE ?";
            params.add("%" + nameLike + "%");
        }

        if (category != null && !category.isEmpty()) {
            sql += " AND category = ?";
            params.add(category);
        }

        if (brand != null && !brand.isEmpty()) {
            sql += " AND brand = ?";
            params.add(brand);
        }

        if (minPrice != null && maxPrice != null) {
            sql += " AND price BETWEEN ? AND ?";
            params.add(minPrice);
            params.add(maxPrice);
        } else if (minPrice != null) {
            sql += " AND price >= ?";
            params.add(minPrice);
        } else if (maxPrice != null) {
            sql += " AND price <= ?";
            params.add(maxPrice);
        }

        // 使用JdbcTemplate查询数据库
        return jdbcTemplate.query(sql, params.toArray(), new BeanPropertyRowMapper<>(Product.class));
    }
}
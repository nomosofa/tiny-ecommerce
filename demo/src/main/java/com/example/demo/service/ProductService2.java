package com.example.demo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
@DS("master_2")
public class ProductService2 {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService2(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //    @Transactional
    public Product addProduct(Product product) {
        // 首先检查具有相同名称的商品是否已存在
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            // 如果存在，抛出异常或返回已存在的实体
            throw new IllegalStateException("Product with name " + product.getName() + " already exists.");
        }

        return addProductToDb(product);
    }

    private Product addProductToDb(Product product) {
        // 此处省略了具体的数据库操作逻辑，只需调用对应的repository保存方法
        return productRepository.save(product);
    }


    public Product update(Product product) {
        // 通过商品名称查找现有商品
        Product existingProduct = productRepository.findByName(product.getName())
                .orElseThrow(() -> new EntityNotFoundException("Product with name " + product.getName() + " not found."));

        // 更新商品的详细信息
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        // 根据需要更新其他字段，但排除了name和category因为这些可能是不变的或者唯一的
        // 如果category也需要更新，你需要先验证新的category是否存在

        return productRepository.save(existingProduct);
    }


    public Optional<Product> findProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Transactional
    public void deleteProductByName(String name) {
        productRepository.deleteByName(name);
    }

    // 查询所有商品，这里可能需要合并两个数据库的查询结果
//    public List<Product> findAllProducts() {
//        return productRepository.findAll();
//    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @DS("master_2")
    public List findAllProducts() {
        return jdbcTemplate.queryForList("select * from products");
    }
}
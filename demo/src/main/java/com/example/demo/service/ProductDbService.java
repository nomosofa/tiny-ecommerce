package com.example.demo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    @DS("#dbIdentifier")
//    public Product saveProduct(Product product, String dbIdentifier) {
//        return productRepository.save(product);
//    }
//
//    @DS("#dbIdentifier")
//    public Optional<Product> findProductByName(String name, String dbIdentifier) {
//        return productRepository.findByName(name);
//    }
//
//    @DS("#dbIdentifier")
//    public void deleteProductByName(String name, String dbIdentifier) {
//        productRepository.deleteByName(name);
//    }
//
//    @DS("#dbIdentifier")
//    public List<Product> findAllProducts(String dbIdentifier) {
//        return productRepository.findAll();
//    }
}
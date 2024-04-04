package com.example.demo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class ProductService {

    private final ProductDbService productDbService;

    @Autowired
    public ProductService(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getProductID(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    public Product addProduct(Product product) {
        // 首先检查具有相同名称的商品是否已存在
        String dbKey = product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2";
        Optional<Product> existingProduct = productDbService.findProductByName(product.getName(), dbKey);
        if (existingProduct.isPresent()) {
            throw new IllegalStateException("Product with name " + product.getName() + " already exists.");
        }

        return productDbService.saveProduct(product, dbKey);
    }

    public Product updateProduct(Product product) {
        String dbKey = product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2";
        return productDbService.saveProduct(product, dbKey);
    }

    public Optional<Product> findProductByName(String name) {
        String dbKey = name.hashCode() % 2 == 0 ? "master_1" : "master_2";
        return productDbService.findProductByName(name, dbKey);
    }

    public void deleteProductByName(String name) {
        String dbKey = name.hashCode() % 2 == 0 ? "master_1" : "master_2";
        productDbService.deleteProductByName(name, dbKey);
    }


    public List<Product> findAllProductsFromDb1() {
        return productDbService.findAllProducts("master_1");
    }

    public List<Product> findAllProductsFromDb2() {
        return productDbService.findAllProducts("master_2");
    }










////    @Transactional
//    public Product addProduct(Product product) {
//        // 首先检查具有相同名称的商品是否已存在
//        Optional<Product> existingProduct = productRepository.findByName(product.getName());
//        if (existingProduct.isPresent()) {
//            // 如果存在，抛出异常或返回已存在的实体
//            throw new IllegalStateException("Product with name " + product.getName() + " already exists.");
//        }
//
//        // 基于名称的hash值决定使用哪个数据库
//        String dbKey = product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2";
//        System.out.println("=========================" + dbKey + "=========================");
//        return addProductToDb(product);
//    }
//
////    @DS("#product.name.hashCode() % 2 == 0 ? 'master_1' : 'master_2'")
//    @DS("master_2")
//    private Product addProductToDb(Product product) {
//        // 此处省略了具体的数据库操作逻辑，只需调用对应的repository保存方法
//        return productRepository.save(product);
//    }
//
//    @DS("#product.name.hashCode() % 2 == 0 ? 'master_1' : 'master_2'")
////    @Transactional
//    public Product update(Product product) {
//        // 通过商品名称查找现有商品
//        Product existingProduct = productRepository.findByName(product.getName())
//                .orElseThrow(() -> new EntityNotFoundException("Product with name " + product.getName() + " not found."));
//
//        // 更新商品的详细信息
//        existingProduct.setDescription(product.getDescription());
//        existingProduct.setPrice(product.getPrice());
//        // 根据需要更新其他字段，但排除了name和category因为这些可能是不变的或者唯一的
//        // 如果category也需要更新，你需要先验证新的category是否存在
//
//        return productRepository.save(existingProduct);
//    }
//
//    @DS("#name.hashCode() % 2 == 0 ? 'master_1' : 'master_2'")
////    @Transactional
//    public Optional<Product> findProductByName(String name) {
//        return productRepository.findByName(name);
//    }
//
//    @DS("#name.hashCode() % 2 == 0 ? 'master_1' : 'master_2'")
////    @Transactional
//    public void deleteProductByName(String name) {
//        productRepository.deleteByName(name);
//    }
//
//    // 查询所有商品，这里可能需要合并两个数据库的查询结果
//    public List<Product> findAllProducts() {
//        List<Product> allProducts = new ArrayList<>();
//        allProducts.addAll(findAllProductsFromDb("master_1"));
//        allProducts.addAll(findAllProductsFromDb("master_2"));
//        return allProducts;
//    }
//
//    @DS("#dbIdentifier")
//    protected List<Product> findAllProductsFromDb(String dbIdentifier) {
//        return productRepository.findAll();
//    }
}
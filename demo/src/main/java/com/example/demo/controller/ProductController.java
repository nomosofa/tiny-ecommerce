package com.example.demo.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.dto.ApiResponse;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductService1;
import com.example.demo.service.ProductService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 揭程
 * @version 1.0
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService1 productService1;
    private final ProductService2 productService2;

    @Autowired
    public ProductController(ProductService1 productService1, ProductService2 productService2) {
        this.productService1 = productService1;
        this.productService2 = productService2;
    }

//    @PostMapping("/add")
//    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product) {
//        System.out.println("addProduct=========================" + (product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2") + "=========================");
//
//        Product savedProduct;
//        if (product.getName().hashCode() % 2 == 0) {
//            savedProduct = productService1.addProduct(product);
//        } else {
//            savedProduct = productService2.addProduct(product);
//        }
//        return ResponseEntity.ok(new ApiResponse(true, "Product processed successfully", savedProduct));
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<ApiResponse> updateProduct(@RequestBody Product product) {
//        System.out.println("updateProduct=========================" + (product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2") + "=========================");
//
//        Product updatedProduct;
//        if (product.getName().hashCode() % 2 == 0) {
//            updatedProduct = productService1.update(product);
//        } else {
//            updatedProduct = productService2.update(product);
//        }
////        Product updatedProduct = productService.updateProduct(product);
//        if (updatedProduct != null) {
//            return ResponseEntity.ok(new ApiResponse(true, "Product updated successfully", updatedProduct));
//        } else {
//            return ResponseEntity.badRequest().body(new ApiResponse(false, "Error updating product", null));
//        }
//    }
//
//    @GetMapping("/{name}")
//    public ResponseEntity<ApiResponse> findProductByName(@PathVariable String name) {
//        System.out.println("findProductByName =========================" + (name.hashCode() % 2 == 0 ? "master_1" : "master_2") + "=========================");
//
//        if (name.hashCode() % 2 == 0) {
//            return productService1.findProductByName(name)
//                    .map(product -> ResponseEntity.ok(new ApiResponse(true, "Product found", product)))
//                    .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Product not found", null)));
//        }
//        return productService2.findProductByName(name)
//                .map(product -> ResponseEntity.ok(new ApiResponse(true, "Product found", product)))
//                .orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "Product not found", null)));
//    }
//
//    @DeleteMapping("/delete/{name}")
//    public ResponseEntity<ApiResponse> deleteProductByName(@PathVariable String name) {
//        System.out.println("deleteProductByName =========================" + (name.hashCode() % 2 == 0 ? "master_1" : "master_2") + "=========================");
//
//        if (name.hashCode() % 2 == 0) {
//            productService1.deleteProductByName(name);
//        } else {
//            productService2.deleteProductByName(name);
//        }
////        productService.deleteProductByName(name);
//        return ResponseEntity.ok(new ApiResponse(true, "Product deleted successfully", null));
//    }
//
//    @GetMapping("/list")
//    public ResponseEntity<ApiResponse> findAllProducts() {
//        List<Product> products = productService1.findAllProducts();
//        List<Product> products2 = productService2.findAllProducts();
//        products.addAll(products2);
//        return ResponseEntity.ok(new ApiResponse(true, "Products retrieved successfully", products));
//    }

}
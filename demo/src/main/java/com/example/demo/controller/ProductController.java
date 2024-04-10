package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 揭程
 * @version 1.0
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productService.findAllProducts();
//        return ResponseEntity.ok(products);
//    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Optional<Product> product = productService.findProductByName(name);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(required = false) String nameLike,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Product> products = productService.searchProducts(nameLike, category, brand, minPrice, maxPrice);

        // 手动实现分页逻辑
        int fromIndex = Math.min(page * size, products.size());
        int toIndex = Math.min((page + 1) * size, products.size());
        List<Product> pagedProducts = products.subList(fromIndex, toIndex);

        List<ProductDTO> productDTOs = pagedProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    private ProductDTO convertToDTO(Product product) {
        // 实现转换逻辑
        return new ProductDTO(product.getName(), product.getPrice(), product.getCategory(), product.getBrand());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody Product product) {
        System.out.println("addProduct=========================" + (product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2") + "=========================");

        Product savedProduct = productService.addProduct(product);

        return ResponseEntity.ok(new ApiResponse(true, "Product processed successfully", savedProduct));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String name) {
        ApiResponse response = productService.deleteProduct(name);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO productDTO) {
        ApiResponse response = productService.updateProduct(productDTO);
        return ResponseEntity.ok(response);
    }

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
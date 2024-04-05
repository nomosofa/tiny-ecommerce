package com.example.demo.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    private final ProductRepository productRepository;
    private final ProductDbService productDbService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDbService productDbService) {
        this.productRepository = productRepository;
        this.productDbService = productDbService;
    }


    // 查询所有商品，这里可能需要合并两个数据库的查询结果
//    public List<Product> findAllProducts() {
//        List<Product> allProducts = new ArrayList<>(productDbService.findAllProducts("master_1"));
//        allProducts.addAll(productDbService.findAllProducts("master_2"));
//        return allProducts;
//    }
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        // 分别从两个数据源获取分页的商品列表
        Page<Product> productsFromDb1 = productDbService.findAllProducts("master_1", pageable);
        Page<Product> productsFromDb2 = productDbService.findAllProducts("master_2", pageable);

        // 合并两个列表
        List<Product> combinedProducts = new ArrayList<>();
        combinedProducts.addAll(productsFromDb1.getContent());
        combinedProducts.addAll(productsFromDb2.getContent());

        // 这里的总页数和总元素数量为简化计算结果，实际应根据需求调整
        return new PageImpl<>(combinedProducts.stream().map(this::convertToDTO).collect(Collectors.toList()), pageable, productsFromDb1.getTotalElements() + productsFromDb2.getTotalElements());
    }


    //    @Transactional
    public Product addProduct(Product product) {
        String dbIdentifier = product.getName().hashCode() % 2 == 0 ? "master_1" : "master_2";
        Optional<Product> existingProduct = productDbService.findProductByName(product.getName(), dbIdentifier);

        if (existingProduct.isPresent()) {
//            return new ApiResponse(false, "Product with name " + product.getName() + " already exists.");
            return null;
        }

        Product savedProduct = productDbService.saveProduct(product, dbIdentifier);
        return savedProduct;
    }

    public Optional<Product> findProductByName(String name) {
        String dbKey = name.hashCode() % 2 == 0 ? "master_1" : "master_2";
        return productDbService.findProductByName(name, dbKey);
    }

    public List<Product> searchProducts(String nameLike, String category, String brand, BigDecimal minPrice, BigDecimal maxPrice) {
        // 此处假设我们需要动态决定数据源，但实际上，搜索可能需要在两个数据源中都执行，然后合并结果
        String dbIdentifier1 = "master_1";
        List<Product> productsFromDb1 = productDbService.searchProductsInDb(nameLike, category, brand, minPrice, maxPrice, dbIdentifier1);

        String dbIdentifier2 = "master_2";
        List<Product> productsFromDb2 = productDbService.searchProductsInDb(nameLike, category, brand, minPrice, maxPrice, dbIdentifier2);

        // 合并两个数据源的结果
        List<Product> combinedResults = new ArrayList<>(productsFromDb1);
        combinedResults.addAll(productsFromDb2);
        return combinedResults;
    }

    private ProductDTO convertToDTO(Product product) {
        // 实现转换逻辑
        return new ProductDTO(product.getName(), product.getPrice(), product.getCategory(), product.getBrand());
    }

    public ApiResponse deleteProduct(String name) {
        String dbIdentifier = name.hashCode() % 2 == 0 ? "master_1" : "master_2";
        boolean deleted = productDbService.deleteProductByName(name, dbIdentifier);

        if (deleted) {
            return new ApiResponse(true, "Product deleted successfully.");
        } else {
            return new ApiResponse(false, "Product not found.");
        }
    }

    public ApiResponse updateProduct(ProductDTO productDTO) {
        String dbIdentifier = productDTO.getName().hashCode() % 2 == 0 ? "master_1" : "master_2";
        boolean updated = productDbService.updateProduct(productDTO, dbIdentifier);

        if (updated) {
            return new ApiResponse(true, "Product updated successfully.");
        } else {
            return new ApiResponse(false, "Product not found.");
        }
    }

}
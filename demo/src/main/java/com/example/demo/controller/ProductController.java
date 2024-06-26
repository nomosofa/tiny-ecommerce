package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PaginatedResponse;
import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Product;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.Comparator;
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

    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public ProductController(CartService cartService, ProductService productService) {
        this.productService = productService;
        this.cartService = cartService;
    }

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

    @GetMapping("/sorted")
    public ResponseEntity<List<ProductDTO>> getAllProductsSorted(
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Product> products = productService.findAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Sort by price in ascending or descending order
        if ("asc".equalsIgnoreCase(sortOrder)) {
            productDTOs.sort(Comparator.comparing(ProductDTO::getPrice));
        } else { // Default to descending order
            productDTOs.sort(Comparator.comparing(ProductDTO::getPrice).reversed());
        }

        // Implement "fake" pagination
        int start = Math.min(page * size, productDTOs.size());
        int end = Math.min((page + 1) * size, productDTOs.size());
        List<ProductDTO> paginatedProducts = productDTOs.subList(start, end);

        return ResponseEntity.ok(paginatedProducts);
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
    public ResponseEntity<PaginatedResponse<ProductDTO>> searchProducts(
            @RequestParam(required = false) String nameLike,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Product> products = productService.searchProducts(nameLike, category, brand, minPrice, maxPrice);

        // 排序
        Comparator<ProductDTO> comparator = Comparator.comparing(ProductDTO::getPrice);
        if ("desc".equalsIgnoreCase(sort)) {
            comparator = comparator.reversed();
        }

        // 转换为DTO并排序
        List<ProductDTO> sortedProductDTOs = products.stream()
                .map(this::convertToDTO)
                .sorted(comparator)
                .collect(Collectors.toList());

        // 总数量
        long totalElements = sortedProductDTOs.size();

        // 手动实现分页逻辑
        int fromIndex = Math.min(page * size, sortedProductDTOs.size());
        int toIndex = Math.min((page + 1) * size, sortedProductDTOs.size());
        List<ProductDTO> pagedProducts = sortedProductDTOs.subList(fromIndex, toIndex);

        return ResponseEntity.ok(new PaginatedResponse<>(pagedProducts, totalElements));
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

        // Delete all cart items with the specified product name
        cartService.deleteCartByProductName(name);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO productDTO) {
        ApiResponse response = productService.updateProduct(productDTO);
        return ResponseEntity.ok(response);
    }

}
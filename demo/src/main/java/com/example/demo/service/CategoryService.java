package com.example.demo.service;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 揭程
 * @version 1.0
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ApiResponse addOrUpdateCategory(CategoryDTO categoryDTO, boolean isUpdate) {
        if(isUpdate && (categoryDTO.getId() == null || !categoryRepository.existsById(categoryDTO.getId()))) {
            return new ApiResponse(false, "Category not found.");
        }

        // 如果是添加操作或者是更新操作但分类名称发生了变化，则检查新名称是否已存在
        if (!isUpdate || (isUpdate && !categoryRepository.findById(categoryDTO.getId())
                .map(Category::getName)
                .orElse("").equals(categoryDTO.getName()))) {
            // 检查分类名称是否已被使用
            if (categoryRepository.existsByName(categoryDTO.getName())) {
                return new ApiResponse(false, "Category name already exists: " + categoryDTO.getName());
            }
        }

        Category category = new Category();
        if (isUpdate) {
            category = categoryRepository.findById(categoryDTO.getId())
                    .orElseThrow(() -> new IllegalStateException("Category with id " + categoryDTO.getId() + " does not exist."));
        }

        category.setName(categoryDTO.getName());
        Category savedCategory = categoryRepository.save(category);
        return new ApiResponse(true, "Category saved successfully.",
                new CategoryDTO(savedCategory.getCategoryID(), savedCategory.getName()));
    }

    @Transactional
    public ApiResponse deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            return new ApiResponse(false, "Category not found.");
        }
        categoryRepository.deleteById(categoryId);
        return new ApiResponse(true, "Category deleted successfully.");
    }

    public ApiResponse findAllCategories() {
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(category.getCategoryID(), category.getName()))
                .collect(Collectors.toList());
        return new ApiResponse(true, "Categories retrieved successfully.", categories);
    }
}

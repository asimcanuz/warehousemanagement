package org.asodev.monolithic.warehousemanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asodev.monolithic.warehousemanagement.converter.CategoryConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateCategoryDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CategoryResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = Category.builder()
                .name(createCategoryDTO.getName())
                .description(createCategoryDTO.getDescription())
                .build();
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }

        if (createCategoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new WMSException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }
        categoryRepository.save(category);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void updateCategory(Long categoryId, CreateCategoryDTO createCategoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new WMSException("Category not found"));

        category.setName(createCategoryDTO.getName());
        category.setDescription(createCategoryDTO.getDescription());

        if (createCategoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryDTO.getParentCategoryId())
                    .orElseThrow(() -> new WMSException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        categoryRepository.save(category);
    }

    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setIsActive(false);
            categoryRepository.save(category);
        }

    }

    @Cacheable(cacheNames = "categories", key = "#categoryId")
    public CategoryResponseDTO getCategoryById(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new WMSException("Category not found");
        }

        return CategoryConverter.fromCategory(category.get());

    }

    @Cacheable(cacheNames = "categoriesList", key = "'list_' + #limit + '_' + #offset")
    public Map<String, Object> getAllCategories(int limit, int offset) {
        Map<String, Object> response = new HashMap<>();
        List<Category> categories = categoryRepository.findAllWithLimit(PageRequest.of(offset, limit));
        List<CategoryResponseDTO> categoryResponseDTOS = categories.stream()
                .map(CategoryConverter::fromCategory)
                .toList();
        response.put("categories", categoryResponseDTOS);
        response.put("total", categoryRepository.count());
        return response;
    }
}

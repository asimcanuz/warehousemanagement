package org.asodev.monolithic.warehousemanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.asodev.monolithic.warehousemanagement.converter.CategoryConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateCategoryDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CategoryResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @CacheEvict(allEntries = true)
    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        Optional<Category> parentCategory = categoryRepository.findById(createCategoryDTO.getParentCategoryId());
        Category category = Category.builder()
                .name(createCategoryDTO.getName())
                .description(createCategoryDTO.getDescription())
                .parentCategory(parentCategory.orElse(null))
                .build();
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }
        categoryRepository.save(category);
    }

    @CachePut(key = "#categoryId")
    public void updateCategory(Long categoryId, CreateCategoryDTO createCategoryDTO) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new WMSException("Category not found");
        }

        Optional<Category> parentCategory = categoryRepository.findById(createCategoryDTO.getParentCategoryId());
        Category updatedCategory = category.get();
        updatedCategory.setName(createCategoryDTO.getName());
        updatedCategory.setDescription(createCategoryDTO.getDescription());
        updatedCategory.setParentCategory(parentCategory.orElse(null));

        categoryRepository.save(updatedCategory);
    }

    @CacheEvict(key = "#categoryId")
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            category.setIsActive(false);
            categoryRepository.save(category);
        }

    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Cacheable
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

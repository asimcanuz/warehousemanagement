package org.asodev.monolithic.warehousemanagement.service;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateCategoryDTO;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @CacheEvict(value = "categories", allEntries = true)
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

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

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

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}

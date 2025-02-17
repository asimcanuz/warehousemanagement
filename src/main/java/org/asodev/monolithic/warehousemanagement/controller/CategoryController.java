package org.asodev.monolithic.warehousemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public GenericResponse<List<Category>> getAllCategories() {
        return GenericResponse.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public GenericResponse<Category> getCategoryById(@PathVariable Long id) {
        return GenericResponse.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    public GenericResponse<Category> createCategory(Category category) {
        return GenericResponse.success(categoryService.createCategory(category));
    }

    @PutMapping
    public GenericResponse<Category> updateCategory(Category category) {
        return GenericResponse.success(categoryService.updateCategory(category));
    }

    @PatchMapping("/{id}")
    public GenericResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return GenericResponse.success(null);
    }

}

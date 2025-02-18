package org.asodev.monolithic.warehousemanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateCategoryDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public GenericResponse<Map<String,Object>> getAllCategories(
            @RequestParam (defaultValue = "10") @Min(1) @Max(100) int limit,
            @RequestParam (defaultValue = "0") @Min(0) int offset
    ) {
        return GenericResponse.success(categoryService.getAllCategories(limit,offset));
    }

    @GetMapping("/{id}")
    public GenericResponse<Category> getCategoryById(@PathVariable Long id) {
        return GenericResponse.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    public GenericResponse<Void> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        categoryService.createCategory(createCategoryDTO);
        return GenericResponse.success(null);
    }

    @PutMapping("/{id}")
    public GenericResponse<Void> updateCategory(@PathVariable Long id,@RequestBody CreateCategoryDTO createCategoryDTO) {
        categoryService.updateCategory(id, createCategoryDTO);
        return GenericResponse.success(null);
    }

    @PatchMapping("/{id}")
    public GenericResponse<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return GenericResponse.success(null);
    }

}

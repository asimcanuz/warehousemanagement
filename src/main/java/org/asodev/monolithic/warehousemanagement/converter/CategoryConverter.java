package org.asodev.monolithic.warehousemanagement.converter;

import org.asodev.monolithic.warehousemanagement.dto.response.CategoryResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Category;

public class CategoryConverter {
    public static Category toCategory(CategoryResponseDTO categoryResponseDTO) {
        return Category.builder()
                .id(categoryResponseDTO.getId())
                .name(categoryResponseDTO.getName())
                .description(categoryResponseDTO.getDescription())
                .isActive(categoryResponseDTO.getIsActive())
                .build();
    }

    public static CategoryResponseDTO fromCategory(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .build();
    }
}

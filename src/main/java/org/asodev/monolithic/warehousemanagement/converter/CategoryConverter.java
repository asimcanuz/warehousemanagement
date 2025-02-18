package org.asodev.monolithic.warehousemanagement.converter;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateCategoryDTO;
import org.asodev.monolithic.warehousemanagement.model.Category;

public class CategoryConverter {
    public static Category toCategory(CreateCategoryDTO createCategoryDTO) {
        return Category.builder()
                .name(createCategoryDTO.getName())
                .description(createCategoryDTO.getDescription())
                .build();
    }
}

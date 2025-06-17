package org.asodev.monolithic.warehousemanagement.converter;

import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Product;

public class ProductConverter {

    public static ProductResponseDTO toProductResponseDTO(Product product) {
        Long categoryId = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(null)
                .categoryId(categoryId)
                .isActive(product.getIsActive())
                .barcode(product.getBarcode())
                .sku(product.getSku())
                .build();
    }
}

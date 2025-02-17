package org.asodev.monolithic.warehousemanagement.converter;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Product;

public class ProductConverter {
    public static Product toProduct(CreateProductDTO createProductDTO) {
        return Product.builder()
                .name(createProductDTO.getName())
                .description(createProductDTO.getDescription())
                .price(createProductDTO.getPrice())
                .isActive(true)
                .quantity(createProductDTO.getQuantity())
                .category(createProductDTO.getCategory())
                .build();
    }

    public static ProductResponseDTO toProductResponseDTO(Product product) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .categoryId(product.getCategory().getId())
                .isActive(product.getIsActive())
                .build();
    }
}

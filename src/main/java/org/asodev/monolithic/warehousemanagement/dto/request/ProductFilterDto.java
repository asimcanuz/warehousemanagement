package org.asodev.monolithic.warehousemanagement.dto.request;

import java.math.BigDecimal;

public record ProductFilterDto(
        String name,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Boolean isActive,
        Long categoryId
) {
}

package org.asodev.monolithic.warehousemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductDTO(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Description is required") String description,
        @NotNull(message = "Price is required") Double price,
        @NotNull(message = "Quantity is required") @Positive(message = "Quantity must be a positive number") int quantity,
        Long categoryId,
        Boolean isActive,
        @NotNull(message = "Stock is required") @PositiveOrZero(message = "Stock cannot be negative") Integer stock,
        @NotNull(message = "Reserved quantity is required") @PositiveOrZero(message = "Reserved quantity cannot be negative") Integer reservedQuantity

) {

}

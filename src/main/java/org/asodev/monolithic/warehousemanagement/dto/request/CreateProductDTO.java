package org.asodev.monolithic.warehousemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateProductDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive number")
    private int quantity;
    private Long categoryId;
    private Boolean isActive;
    @NotBlank(message = "SKU is required")
    @NotNull(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Barcode is required")
    @NotNull(message = "Barcode is required")
    private String barcode;

}

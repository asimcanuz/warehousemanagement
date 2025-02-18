package org.asodev.monolithic.warehousemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCategoryDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    private Long parentCategoryId;
}

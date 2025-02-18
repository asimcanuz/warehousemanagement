package org.asodev.monolithic.warehousemanagement.dto.response;

import lombok.*;
import org.asodev.monolithic.warehousemanagement.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Long parentCategoryId;


}

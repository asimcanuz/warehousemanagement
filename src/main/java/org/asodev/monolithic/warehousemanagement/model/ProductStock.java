package org.asodev.monolithic.warehousemanagement.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class ProductStock extends BaseModel {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;

}

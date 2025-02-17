package org.asodev.monolithic.warehousemanagement.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}

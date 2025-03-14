package org.asodev.monolithic.warehousemanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Auditable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private Boolean isActive=true;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}

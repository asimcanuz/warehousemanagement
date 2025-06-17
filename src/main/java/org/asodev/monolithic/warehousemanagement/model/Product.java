package org.asodev.monolithic.warehousemanagement.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_price", columnList = "price"),
        @Index(name = "idx_product_category_id", columnList = "category_id")
})
public class Product extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Column(name = "sku", unique = true)
    private String sku;
    @Column(name = "barcode", unique = true)
    private String barcode;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductStock productStock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Boolean isActive = true;

    @ManyToMany(mappedBy = "products")
    private List<ProductCampaign> campaigns;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductPriceHistory> priceHistory;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductImage> images;
}
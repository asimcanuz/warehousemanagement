package org.asodev.monolithic.warehousemanagement.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_skus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSKU {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String sku;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id")
  private Product product;

  @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductBarcode> barcodes;

  @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductUom> uoms;

  @ManyToOne(optional = false)
  @JoinColumn(name = "base_uom_id")
  private Uom baseUom;

  @Column(nullable = false)
  private Boolean trackLot;

  @Column(nullable = false)
  private Boolean trackSerial;

  @Column(nullable = false)
  private Boolean expiryRequired;
}

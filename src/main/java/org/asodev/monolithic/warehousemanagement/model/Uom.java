package org.asodev.monolithic.warehousemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "uoms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Uom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String code; // Örn: PIECE, BOX, KG

  @Column(nullable = false)
  private String name; // Örn: Adet, Kutu, Kilogram
}

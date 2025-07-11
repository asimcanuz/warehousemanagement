package org.asodev.monolithic.warehousemanagement.repository;

import org.asodev.monolithic.warehousemanagement.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}

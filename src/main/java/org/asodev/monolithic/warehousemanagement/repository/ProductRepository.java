package org.asodev.monolithic.warehousemanagement.repository;

import jakarta.persistence.EntityManager;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.isActive = true")
    List<Product> findAllWithLimit(PageRequest pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:isActive IS NULL OR p.isActive = :isActive)")
    List<Product> searchProducts(String name, Long categoryId, Boolean isActive, PageRequest pageable);

    EntityManager getEntityManager();
}

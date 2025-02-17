package org.asodev.monolithic.warehousemanagement.repository;

import org.asodev.monolithic.warehousemanagement.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

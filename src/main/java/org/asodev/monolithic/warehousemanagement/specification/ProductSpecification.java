package org.asodev.monolithic.warehousemanagement.specification;


import jakarta.persistence.criteria.Predicate;
import org.asodev.monolithic.warehousemanagement.dto.request.ProductFilterDto;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> filterProducts(ProductFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.name() != null && !filter.name().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.name().toLowerCase() + "%"));
            }

            if (filter.minPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"), filter.minPrice()));
            }

            if (filter.maxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"), filter.maxPrice()));
            }

            if (filter.categoryId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("category").get("id"), filter.categoryId()));
            }

            if (filter.isActive() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("isActive"), filter.isActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
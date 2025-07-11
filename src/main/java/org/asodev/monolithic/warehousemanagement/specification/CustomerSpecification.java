package org.asodev.monolithic.warehousemanagement.specification;

import java.util.ArrayList;
import java.util.List;

import org.asodev.monolithic.warehousemanagement.dto.request.CustomerFilterDTO;
import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class CustomerSpecification {

  public static Specification<Customer> filterCustomers(CustomerFilterDTO filter) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.name() != null && !filter.name().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("name")),
            "%" + filter.name().toLowerCase() + "%"));
      }

      if (filter.email() != null && !filter.email().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("email")),
            "%" + filter.email().toLowerCase() + "%"));
      }

      if (filter.type() != null) {
        predicates.add(criteriaBuilder.equal(root.get("type"), filter.type()));
      }

      if (filter.isActive() != null) {
        predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.isActive()));
      }

      if (filter.phone() != null && !filter.phone().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("phone")),
            "%" + filter.phone().toLowerCase() + "%"));
      }

      if (filter.contactPerson() != null && !filter.contactPerson().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("contactPerson")),
            "%" + filter.contactPerson().toLowerCase() + "%"));
      }

      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}

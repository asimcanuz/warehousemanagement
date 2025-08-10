package org.asodev.monolithic.warehousemanagement.specification;

import java.util.ArrayList;
import java.util.List;

import org.asodev.monolithic.warehousemanagement.dto.request.AddressFilterDTO;
import org.asodev.monolithic.warehousemanagement.model.Address;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class AddressSpecification {

  public static Specification<Address> filterAddresses(AddressFilterDTO filter) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.type() != null) {
        predicates.add(criteriaBuilder.equal(root.get("type"), filter.type()));
      }

      if (filter.city() != null && !filter.city().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("city")),
            "%" + filter.city().toLowerCase() + "%"));
      }

      if (filter.state() != null && !filter.state().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("state")),
            "%" + filter.state().toLowerCase() + "%"));
      }

      if (filter.country() != null && !filter.country().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("country")),
            "%" + filter.country().toLowerCase() + "%"));
      }

      if (filter.postalCode() != null && !filter.postalCode().trim().isEmpty()) {
        predicates.add(criteriaBuilder.like(
            criteriaBuilder.lower(root.get("postalCode")),
            "%" + filter.postalCode().toLowerCase() + "%"));
      }

      if (filter.isActive() != null) {
        predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.isActive()));
      }

      if (filter.customerId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), filter.customerId()));
      }

      return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }
}

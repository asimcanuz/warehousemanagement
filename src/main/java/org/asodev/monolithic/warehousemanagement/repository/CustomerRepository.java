package org.asodev.monolithic.warehousemanagement.repository;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.asodev.monolithic.warehousemanagement.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
  List<Customer> findByIsActiveTrue();

  List<Customer> findByType(CustomerType type);

  List<Customer> findByTypeAndIsActiveTrue(CustomerType type);

  List<Customer> findByNameContainingIgnoreCase(String name);

  List<Customer> findByEmailContainingIgnoreCase(String email);
}
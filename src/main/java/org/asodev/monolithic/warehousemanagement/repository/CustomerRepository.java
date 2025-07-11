package org.asodev.monolithic.warehousemanagement.repository;

import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
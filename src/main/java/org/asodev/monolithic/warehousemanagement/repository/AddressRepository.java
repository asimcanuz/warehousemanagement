package org.asodev.monolithic.warehousemanagement.repository;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.Address;
import org.asodev.monolithic.warehousemanagement.model.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
  List<Address> findByCustomerId(Long customerId);

  List<Address> findByCustomerIdAndIsActiveTrue(Long customerId);

  List<Address> findByType(AddressType type);

  List<Address> findByTypeAndIsActiveTrue(AddressType type);

  List<Address> findByIsActiveTrue();

  List<Address> findByCityContainingIgnoreCase(String city);

  List<Address> findByStateContainingIgnoreCase(String state);

  List<Address> findByCountryContainingIgnoreCase(String country);

  List<Address> findByPostalCodeContainingIgnoreCase(String postalCode);
}

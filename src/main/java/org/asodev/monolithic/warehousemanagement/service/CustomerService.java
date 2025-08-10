package org.asodev.monolithic.warehousemanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asodev.monolithic.warehousemanagement.converter.CustomerConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.CustomerFilterDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.AddressResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CustomerResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.asodev.monolithic.warehousemanagement.repository.CustomerRepository;
import org.asodev.monolithic.warehousemanagement.specification.CustomerSpecification;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "customers")
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final AddressService addressService;

  @CachePut(value = "customers", key = "#result.id", unless = "#result == null")
  @Transactional
  public CustomerResponseDTO createCustomer(CreateCustomerDTO createCustomerDTO) {
    log.info("Creating new customer: {}", createCustomerDTO.name());

    try {
      Customer customer = Customer.builder()
          .name(createCustomerDTO.name())
          .email(createCustomerDTO.email())
          .type(createCustomerDTO.type())
          .phone(createCustomerDTO.phone())
          .contactPerson(createCustomerDTO.contactPerson())
          .isActive(true)
          .build();

      Customer savedCustomer = customerRepository.save(customer);
      log.info("Customer created successfully with ID: {} - Name: {}", savedCustomer.getId(), savedCustomer.getName());

      return CustomerConverter.toCustomerResponseDTO(savedCustomer);
    } catch (Exception e) {
      log.error("Error creating customer: {}", createCustomerDTO, e);
      throw new WMSException("Failed to create customer: " + e.getMessage());
    }
  }

  @Cacheable(value = "customers", key = "#customerId")
  public CustomerResponseDTO getCustomerById(Long customerId) {
    log.info("Fetching customer with ID: {}", customerId);

    Optional<Customer> customer = customerRepository.findById(customerId);

    if (customer.isEmpty()) {
      log.error("Customer not found with ID: {}", customerId);
      throw new WMSException("Customer not found with ID: " + customerId);
    }

    return CustomerConverter.toCustomerResponseDTO(customer.get());
  }

  @Cacheable(value = "customersList", key = "'list_' + #limit + '_' + #offset + '_' + #filter?.hashCode()")
  public Map<String, Object> getAllCustomers(int limit, int offset, CustomerFilterDTO filter) {
    log.info("Fetching customers with limit: {}, offset: {}, filter: {}", limit, offset, filter);

    Map<String, Object> response = new HashMap<>();
    Page<Customer> customers;

    if (filter != null) {
      Specification<Customer> specification = CustomerSpecification.filterCustomers(filter);
      customers = customerRepository.findAll(specification, PageRequest.of(offset, limit));
    } else {
      customers = customerRepository.findAll(PageRequest.of(offset, limit));
    }

    List<CustomerResponseDTO> customerResponseDTOs = customers.stream()
        .map(CustomerConverter::toCustomerResponseDTO)
        .toList();

    response.put("customers", customerResponseDTOs);
    response.put("total", customers.getTotalElements());
    response.put("totalPages", customers.getTotalPages());
    response.put("currentPage", offset);
    response.put("pageSize", limit);

    return response;
  }

  @CachePut(value = "customers", key = "#customerId")
  @CacheEvict(value = "customersList", allEntries = true)
  @Transactional
  public CustomerResponseDTO updateCustomer(Long customerId, UpdateCustomerDTO updateCustomerDTO) {
    log.info("Updating customer with ID: {}", customerId);

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> {
          log.error("Customer not found with ID: {}", customerId);
          return new WMSException("Customer not found with ID: " + customerId);
        });

    try {
      customer.setName(updateCustomerDTO.name());
      customer.setEmail(updateCustomerDTO.email());
      customer.setType(updateCustomerDTO.type());
      customer.setPhone(updateCustomerDTO.phone());
      customer.setContactPerson(updateCustomerDTO.contactPerson());

      if (updateCustomerDTO.isActive() != null) {
        customer.setIsActive(updateCustomerDTO.isActive());
      }

      Customer updatedCustomer = customerRepository.save(customer);
      log.info("Customer updated successfully with ID: {}", updatedCustomer.getId());

      return CustomerConverter.toCustomerResponseDTO(updatedCustomer);
    } catch (Exception e) {
      log.error("Error updating customer with ID: {}", customerId, e);
      throw new WMSException("Failed to update customer: " + e.getMessage());
    }
  }

  @CacheEvict(value = { "customers", "customersList" }, allEntries = true)
  @Transactional
  public void deleteCustomer(Long customerId) {
    log.info("Soft deleting customer with ID: {}", customerId);

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> {
          log.error("Customer not found with ID: {}", customerId);
          return new WMSException("Customer not found with ID: " + customerId);
        });

    try {
      customer.setIsActive(false);
      customerRepository.save(customer);
      log.info("Customer soft deleted successfully with ID: {}", customerId);
    } catch (Exception e) {
      log.error("Error deleting customer with ID: {}", customerId, e);
      throw new WMSException("Failed to delete customer: " + e.getMessage());
    }
  }

  @CacheEvict(value = { "customers", "customersList" }, allEntries = true)
  @Transactional
  public void hardDeleteCustomer(Long customerId) {
    log.info("Hard deleting customer with ID: {}", customerId);

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> {
          log.error("Customer not found with ID: {}", customerId);
          return new WMSException("Customer not found with ID: " + customerId);
        });

    try {
      customerRepository.delete(customer);
      log.info("Customer hard deleted successfully with ID: {}", customerId);
    } catch (Exception e) {
      log.error("Error hard deleting customer with ID: {}", customerId, e);
      throw new WMSException("Failed to hard delete customer: " + e.getMessage());
    }
  }

  public boolean existsById(Long customerId) {
    return customerRepository.existsById(customerId);
  }

  public List<CustomerResponseDTO> getActiveCustomers() {
    log.info("Fetching all active customers");

    List<Customer> activeCustomers = customerRepository.findByIsActiveTrue();
    return activeCustomers.stream()
        .map(CustomerConverter::toCustomerResponseDTO)
        .toList();
  }

  public List<CustomerResponseDTO> getCustomersByType(
      org.asodev.monolithic.warehousemanagement.model.CustomerType type) {
    log.info("Fetching customers by type: {}", type);

    List<Customer> customers = customerRepository.findByType(type);
    return customers.stream()
        .map(CustomerConverter::toCustomerResponseDTO)
        .toList();
  }

  // Address management methods - delegating to AddressService
  @CacheEvict(value = { "customers", "customersList" }, allEntries = true)
  @Transactional
  public CustomerResponseDTO addAddressToCustomer(Long customerId, CreateAddressDTO addressDTO) {
    log.info("Adding address to customer with ID: {}", customerId);

    // Use AddressService to create the address
    addressService.createAddress(customerId, addressDTO);

    // Return updated customer data
    return getCustomerById(customerId);
  }

  @CacheEvict(value = { "customers", "customersList" }, allEntries = true)
  @Transactional
  public CustomerResponseDTO updateCustomerAddress(Long customerId, Long addressId, UpdateAddressDTO addressDTO) {
    log.info("Updating address {} for customer with ID: {}", addressId, customerId);

    // Verify customer exists
    if (!customerRepository.existsById(customerId)) {
      log.error("Customer not found with ID: {}", customerId);
      throw new WMSException("Customer not found with ID: " + customerId);
    }

    // Use AddressService to update the address
    addressService.updateAddress(addressId, addressDTO);

    // Return updated customer data
    return getCustomerById(customerId);
  }

  @CacheEvict(value = { "customers", "customersList" }, allEntries = true)
  @Transactional
  public void deleteCustomerAddress(Long customerId, Long addressId) {
    log.info("Deleting address {} for customer with ID: {}", addressId, customerId);

    // Verify customer exists
    if (!customerRepository.existsById(customerId)) {
      log.error("Customer not found with ID: {}", customerId);
      throw new WMSException("Customer not found with ID: " + customerId);
    }

    // Use AddressService to delete the address
    addressService.deleteAddress(addressId);

    log.info("Address deleted successfully for customer with ID: {}", customerId);
  }

  // Additional address-related methods using AddressService
  public List<AddressResponseDTO> getCustomerAddresses(
      Long customerId) {
    log.info("Fetching addresses for customer with ID: {}", customerId);
    return addressService.getAddressesByCustomerId(customerId);
  }

  public List<AddressResponseDTO> getCustomerActiveAddresses(
      Long customerId) {
    log.info("Fetching active addresses for customer with ID: {}", customerId);
    return addressService.getActiveAddressesByCustomerId(customerId);
  }

  // Legacy method for backward compatibility
  @Deprecated
  public void save(CreateCustomerDTO createCustomerDTO) {
    createCustomer(createCustomerDTO);
  }
}

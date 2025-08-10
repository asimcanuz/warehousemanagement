package org.asodev.monolithic.warehousemanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asodev.monolithic.warehousemanagement.converter.AddressConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.AddressFilterDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.AddressResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Address;
import org.asodev.monolithic.warehousemanagement.model.AddressType;
import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.asodev.monolithic.warehousemanagement.repository.AddressRepository;
import org.asodev.monolithic.warehousemanagement.repository.CustomerRepository;
import org.asodev.monolithic.warehousemanagement.specification.AddressSpecification;
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
@CacheConfig(cacheNames = "addresses")
public class AddressService {

  private final AddressRepository addressRepository;
  private final CustomerRepository customerRepository;

  @CachePut(value = "addresses", key = "#result.id", unless = "#result == null")
  @Transactional
  public AddressResponseDTO createAddress(Long customerId, CreateAddressDTO createAddressDTO) {
    log.info("Creating new address for customer ID: {}", customerId);

    Customer customer = customerRepository.findById(customerId)
        .orElseThrow(() -> {
          log.error("Customer not found with ID: {}", customerId);
          return new WMSException("Customer not found with ID: " + customerId);
        });

    try {
      Address address = Address.builder()
          .type(createAddressDTO.type())
          .street(createAddressDTO.street())
          .city(createAddressDTO.city())
          .state(createAddressDTO.state())
          .postalCode(createAddressDTO.postalCode())
          .country(createAddressDTO.country())
          .additionalInfo(createAddressDTO.additionalInfo())
          .customer(customer)
          .isActive(true)
          .build();

      Address savedAddress = addressRepository.save(address);
      log.info("Address created successfully with ID: {} for customer: {}", savedAddress.getId(), customerId);

      return AddressConverter.toAddressResponseDTO(savedAddress);
    } catch (Exception e) {
      log.error("Error creating address for customer ID: {}", customerId, e);
      throw new WMSException("Failed to create address: " + e.getMessage());
    }
  }

  @Cacheable(value = "addresses", key = "#addressId")
  public AddressResponseDTO getAddressById(Long addressId) {
    log.info("Fetching address with ID: {}", addressId);

    Optional<Address> address = addressRepository.findById(addressId);

    if (address.isEmpty()) {
      log.error("Address not found with ID: {}", addressId);
      throw new WMSException("Address not found with ID: " + addressId);
    }

    return AddressConverter.toAddressResponseDTO(address.get());
  }

  @Cacheable(value = "addressesList", key = "'list_' + #limit + '_' + #offset + '_' + #filter?.hashCode()")
  public Map<String, Object> getAllAddresses(int limit, int offset, AddressFilterDTO filter) {
    log.info("Fetching addresses with limit: {}, offset: {}, filter: {}", limit, offset, filter);

    Map<String, Object> response = new HashMap<>();
    Page<Address> addresses;

    if (filter != null) {
      Specification<Address> specification = AddressSpecification.filterAddresses(filter);
      addresses = addressRepository.findAll(specification, PageRequest.of(offset, limit));
    } else {
      addresses = addressRepository.findAll(PageRequest.of(offset, limit));
    }

    List<AddressResponseDTO> addressResponseDTOs = addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();

    response.put("addresses", addressResponseDTOs);
    response.put("total", addresses.getTotalElements());
    response.put("totalPages", addresses.getTotalPages());
    response.put("currentPage", offset);
    response.put("pageSize", limit);

    return response;
  }

  @Cacheable(value = "customerAddresses", key = "#customerId")
  public List<AddressResponseDTO> getAddressesByCustomerId(Long customerId) {
    log.info("Fetching addresses for customer ID: {}", customerId);

    // Verify customer exists
    if (!customerRepository.existsById(customerId)) {
      log.error("Customer not found with ID: {}", customerId);
      throw new WMSException("Customer not found with ID: " + customerId);
    }

    List<Address> addresses = addressRepository.findByCustomerId(customerId);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getActiveAddressesByCustomerId(Long customerId) {
    log.info("Fetching active addresses for customer ID: {}", customerId);

    // Verify customer exists
    if (!customerRepository.existsById(customerId)) {
      log.error("Customer not found with ID: {}", customerId);
      throw new WMSException("Customer not found with ID: " + customerId);
    }

    List<Address> addresses = addressRepository.findByCustomerIdAndIsActiveTrue(customerId);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  @CachePut(value = "addresses", key = "#addressId")
  @CacheEvict(value = { "addressesList", "customerAddresses" }, allEntries = true)
  @Transactional
  public AddressResponseDTO updateAddress(Long addressId, UpdateAddressDTO updateAddressDTO) {
    log.info("Updating address with ID: {}", addressId);

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> {
          log.error("Address not found with ID: {}", addressId);
          return new WMSException("Address not found with ID: " + addressId);
        });

    try {
      address.setType(updateAddressDTO.type());
      address.setStreet(updateAddressDTO.street());
      address.setCity(updateAddressDTO.city());
      address.setState(updateAddressDTO.state());
      address.setPostalCode(updateAddressDTO.postalCode());
      address.setCountry(updateAddressDTO.country());
      address.setAdditionalInfo(updateAddressDTO.additionalInfo());

      if (updateAddressDTO.isActive() != null) {
        address.setIsActive(updateAddressDTO.isActive());
      }

      Address updatedAddress = addressRepository.save(address);
      log.info("Address updated successfully with ID: {}", updatedAddress.getId());

      return AddressConverter.toAddressResponseDTO(updatedAddress);
    } catch (Exception e) {
      log.error("Error updating address with ID: {}", addressId, e);
      throw new WMSException("Failed to update address: " + e.getMessage());
    }
  }

  @CacheEvict(value = { "addresses", "addressesList", "customerAddresses" }, allEntries = true)
  @Transactional
  public void deleteAddress(Long addressId) {
    log.info("Soft deleting address with ID: {}", addressId);

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> {
          log.error("Address not found with ID: {}", addressId);
          return new WMSException("Address not found with ID: " + addressId);
        });

    try {
      address.setIsActive(false);
      addressRepository.save(address);
      log.info("Address soft deleted successfully with ID: {}", addressId);
    } catch (Exception e) {
      log.error("Error deleting address with ID: {}", addressId, e);
      throw new WMSException("Failed to delete address: " + e.getMessage());
    }
  }

  @CacheEvict(value = { "addresses", "addressesList", "customerAddresses" }, allEntries = true)
  @Transactional
  public void hardDeleteAddress(Long addressId) {
    log.info("Hard deleting address with ID: {}", addressId);

    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> {
          log.error("Address not found with ID: {}", addressId);
          return new WMSException("Address not found with ID: " + addressId);
        });

    try {
      addressRepository.delete(address);
      log.info("Address hard deleted successfully with ID: {}", addressId);
    } catch (Exception e) {
      log.error("Error hard deleting address with ID: {}", addressId, e);
      throw new WMSException("Failed to hard delete address: " + e.getMessage());
    }
  }

  public boolean existsById(Long addressId) {
    return addressRepository.existsById(addressId);
  }

  public List<AddressResponseDTO> getActiveAddresses() {
    log.info("Fetching all active addresses");

    List<Address> activeAddresses = addressRepository.findByIsActiveTrue();
    return activeAddresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getAddressesByType(AddressType type) {
    log.info("Fetching addresses by type: {}", type);

    List<Address> addresses = addressRepository.findByType(type);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getActiveAddressesByType(AddressType type) {
    log.info("Fetching active addresses by type: {}", type);

    List<Address> addresses = addressRepository.findByTypeAndIsActiveTrue(type);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getAddressesByCity(String city) {
    log.info("Fetching addresses by city: {}", city);

    List<Address> addresses = addressRepository.findByCityContainingIgnoreCase(city);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getAddressesByState(String state) {
    log.info("Fetching addresses by state: {}", state);

    List<Address> addresses = addressRepository.findByStateContainingIgnoreCase(state);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }

  public List<AddressResponseDTO> getAddressesByCountry(String country) {
    log.info("Fetching addresses by country: {}", country);

    List<Address> addresses = addressRepository.findByCountryContainingIgnoreCase(country);
    return addresses.stream()
        .map(AddressConverter::toAddressResponseDTO)
        .toList();
  }
}

package org.asodev.monolithic.warehousemanagement.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.asodev.monolithic.warehousemanagement.dto.response.AddressResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CustomerResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Address;
import org.asodev.monolithic.warehousemanagement.model.Customer;

public class CustomerConverter {

  public static CustomerResponseDTO toCustomerResponseDTO(Customer customer) {
    List<AddressResponseDTO> addressDTOs = null;
    if (customer.getAddresses() != null) {
      addressDTOs = customer.getAddresses().stream()
          .map(CustomerConverter::toAddressResponseDTO)
          .collect(Collectors.toList());
    }

    return CustomerResponseDTO.builder()
        .id(customer.getId())
        .name(customer.getName())
        .email(customer.getEmail())
        .type(customer.getType())
        .phone(customer.getPhone())
        .contactPerson(customer.getContactPerson())
        .isActive(customer.getIsActive())
        .addresses(addressDTOs)
        .createdAt(customer.getCreatedAt())
        .updatedAt(customer.getUpdatedAt())
        .build();
  }

  public static AddressResponseDTO toAddressResponseDTO(Address address) {
    return AddressResponseDTO.builder()
        .id(address.getId())
        .type(address.getType())
        .street(address.getStreet())
        .city(address.getCity())
        .state(address.getState())
        .postalCode(address.getPostalCode())
        .country(address.getCountry())
        .additionalInfo(address.getAdditionalInfo())
        .isActive(address.getIsActive())
        .build();
  }
}

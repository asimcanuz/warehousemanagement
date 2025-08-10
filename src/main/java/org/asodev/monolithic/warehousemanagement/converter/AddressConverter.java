package org.asodev.monolithic.warehousemanagement.converter;

import org.asodev.monolithic.warehousemanagement.dto.response.AddressResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Address;

public class AddressConverter {

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

  public static Address toAddress(AddressResponseDTO addressResponseDTO) {
    return Address.builder()
        .id(addressResponseDTO.getId())
        .type(addressResponseDTO.getType())
        .street(addressResponseDTO.getStreet())
        .city(addressResponseDTO.getCity())
        .state(addressResponseDTO.getState())
        .postalCode(addressResponseDTO.getPostalCode())
        .country(addressResponseDTO.getCountry())
        .additionalInfo(addressResponseDTO.getAdditionalInfo())
        .isActive(addressResponseDTO.getIsActive())
        .build();
  }
}

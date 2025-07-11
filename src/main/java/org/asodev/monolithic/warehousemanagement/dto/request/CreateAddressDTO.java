package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.AddressType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAddressDTO(
    @NotNull(message = "Address type is required") AddressType type,
    @NotBlank(message = "Street is required") String street,
    @NotBlank(message = "City is required") String city,
    @NotBlank(message = "State is required") String state,
    @NotBlank(message = "Postal code is required") String postalCode,
    @NotBlank(message = "Country is required") String country,
    String additionalInfo) {
}

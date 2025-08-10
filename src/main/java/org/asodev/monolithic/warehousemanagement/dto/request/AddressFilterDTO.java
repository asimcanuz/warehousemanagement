package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.AddressType;

public record AddressFilterDTO(
    AddressType type,
    String city,
    String state,
    String country,
    String postalCode,
    Boolean isActive,
    Long customerId) {
}

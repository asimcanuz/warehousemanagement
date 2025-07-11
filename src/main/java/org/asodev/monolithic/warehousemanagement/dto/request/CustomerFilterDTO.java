package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.CustomerType;

public record CustomerFilterDTO(
    String name,
    String email,
    CustomerType type,
    Boolean isActive,
    String phone,
    String contactPerson) {
}

package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.Address;

public record WarehouseCreateDTO(
    String code,
    String name,
    String status,
    String timezone,
    Address address) {
}

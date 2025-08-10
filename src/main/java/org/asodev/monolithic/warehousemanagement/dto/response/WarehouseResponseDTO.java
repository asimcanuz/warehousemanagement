package org.asodev.monolithic.warehousemanagement.dto.response;

public record WarehouseResponseDTO(
    Long id,
    String code,
    String name,
    String status,
    String timezone,
    String createdAt,
    String updatedAt,
    Long version) {
}
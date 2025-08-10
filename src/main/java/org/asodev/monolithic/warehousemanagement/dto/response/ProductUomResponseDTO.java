package org.asodev.monolithic.warehousemanagement.dto.response;

public record ProductUomResponseDTO(
    Long id,
    String uomCode,
    String uomName,
    Double convToBase) {
}

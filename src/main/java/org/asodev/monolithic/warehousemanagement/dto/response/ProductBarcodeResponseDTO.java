package org.asodev.monolithic.warehousemanagement.dto.response;

public record ProductBarcodeResponseDTO(
    Long id,
    String barcode,
    String type) {
}

package org.asodev.monolithic.warehousemanagement.dto.response;

import java.util.List;

public record ProductSKUResponseDTO(
    Long id,
    String sku,
    String baseUomCode,
    String baseUomName,
    Boolean trackLot,
    Boolean trackSerial,
    Boolean expiryRequired,
    List<ProductBarcodeResponseDTO> barcodes,
    List<ProductUomResponseDTO> uoms) {
}

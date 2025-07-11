package org.asodev.monolithic.warehousemanagement.dto.response;

public record ProductStockResponse(
    Long id,
    String productName,
    Integer quantity,
    Integer reservedQuantity,
    Integer availableQuantity) {

  public ProductStockResponse(Long id, String productName, Integer quantity, Integer reservedQuantity,
      Integer availableQuantity) {
    this.id = id;
    this.productName = productName;
    this.quantity = quantity;
    this.reservedQuantity = reservedQuantity;
    this.availableQuantity = availableQuantity;
  }

}
package org.asodev.monolithic.warehousemanagement.model;

public enum AddressType {
  SHIPPING("Shipping"),
  BILLING("Billing"),
  WAREHOUSE("Warehouse"),
  OFFICE("Office"),
  OTHER("Other");

  private final String description;

  AddressType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

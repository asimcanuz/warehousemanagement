package org.asodev.monolithic.warehousemanagement.model;

public enum CustomerType {
  COMPANY("company"), INDIVIDUAL("individual");

  private final String type;

  CustomerType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}

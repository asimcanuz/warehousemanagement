package org.asodev.monolithic.warehousemanagement.model;

public enum LocationType {
  BIN("Bin"),
  STAGE("Stage"),
  PICK("Pick"),
  BULK("Bulk"),
  QC("Quality Control");

  private final String displayName;

  LocationType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}

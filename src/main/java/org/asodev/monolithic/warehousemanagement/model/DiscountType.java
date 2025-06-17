package org.asodev.monolithic.warehousemanagement.model;

public enum DiscountType {
    PERCENTAGE("Percentage"),
    FIXED_AMOUNT("Fixed Amount");

    private final String description;

    DiscountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

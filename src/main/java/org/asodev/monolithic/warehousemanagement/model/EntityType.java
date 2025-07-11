package org.asodev.monolithic.warehousemanagement.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityType {
    PRODUCT("product");

    private final String type;

    EntityType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static EntityType fromString(String type) {
        return Arrays.stream(EntityType.values())
                .filter(entityType -> entityType.type.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid EntityType: " + type));
    }

    @Override
    public String toString() {
        return type;
    }
}

package org.asodev.monolithic.warehousemanagement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

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
        for (EntityType entityType : EntityType.values()) {
            if (entityType.type.equalsIgnoreCase(type)) {
                return entityType;
            }
        }

        return Arrays.stream(EntityType.values()).filter(entityType -> entityType.type.equalsIgnoreCase(type)).findFirst().orElseThrow(()-> new IllegalArgumentException("Invalid EntityType: " + type));
    }

    @Override
    public String toString() {
        return type;
    }
}

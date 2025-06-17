package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.CustomerType;

public record CreateCustomerDTO(
        String name,
        String email,
        CustomerType type,
        String phone) {

}

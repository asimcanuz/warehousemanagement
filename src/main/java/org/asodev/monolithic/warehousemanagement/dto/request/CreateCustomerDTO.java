package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.CustomerType;

import lombok.Builder;

@Builder
public record CreateCustomerDTO(
                String name,
                String email,
                CustomerType type,
                String address,
                String phone) {

}

package org.asodev.monolithic.warehousemanagement.dto.request;

import org.asodev.monolithic.warehousemanagement.model.CustomerType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerDTO(
                @NotBlank(message = "Name is required") String name,
                @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
                @NotNull(message = "Customer type is required") CustomerType type,
                String phone,
                String contactPerson) {
}

package org.asodev.monolithic.warehousemanagement.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String PRODUCT_ALREADY_EXISTS = "Product already exists";
    public static final String PRODUCT_NOT_AVAILABLE = "Product not available";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_NAME_REQUIRED = "Customer name is required";
}

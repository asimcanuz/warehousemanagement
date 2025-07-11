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
    public static final String CUSTOMER_EMAIL_REQUIRED = "Customer email is required";
    public static final String STOCK_NOT_FOUND = "Stock not found";
    public static final String IMAGE_NOT_BELONG_TO_PRODUCT = "Image does not belong to the product";
    public static final String IMAGE_NOT_FOUND = "Image not found";
    public static final String FILE_NOT_FOUND = "File not found";
}

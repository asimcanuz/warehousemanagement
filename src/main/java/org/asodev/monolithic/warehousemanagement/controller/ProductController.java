package org.asodev.monolithic.warehousemanagement.controller;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.ProductFilterDto;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated

@Tag(name= "Product Management", description = "Operations related to product management")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/list")
    @Operation(summary = "Get all products",
               description = "Retrieve a paginated list of all products in the warehouse")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Cacheable(value = "products", key = "#filterDto")
    public GenericResponse<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit,
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestBody(required = false) ProductFilterDto filterDto
            ) {
        return GenericResponse.success(productService.getAllProducts(limit, offset, filterDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID",
               description = "Retrieve a specific product by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return GenericResponse.success(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Create new product",
               description = "Add a new product to the warehouse inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<Void> createProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        productService.createProduct(productDTO);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product",
               description = "Update all details of an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully updated"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<Void> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Delete product",
               description = "Soft delete a product from the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public GenericResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return GenericResponse.success(null);
    }
}

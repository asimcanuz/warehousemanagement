package org.asodev.monolithic.warehousemanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public GenericResponse<Map<String,Object>> getAllProducts(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        return GenericResponse.success(productService.getAllProducts(limit, offset));
    }

    @GetMapping("/{id}")
    public GenericResponse<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return GenericResponse.success(productService.getProductById(id));
    }

    @PostMapping
    public GenericResponse<Void> createProduct(@Valid @RequestBody CreateProductDTO productDTO) {
        productService.createProduct(productDTO);
        return GenericResponse.success(null,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public GenericResponse<Void> updateProduct(@PathVariable Long id , @RequestBody CreateProductDTO productDTO) {
        productService.updateProduct(id,productDTO);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public GenericResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return GenericResponse.success(null);
    }
}

package org.asodev.monolithic.warehousemanagement.service;

import org.asodev.monolithic.warehousemanagement.model.ProductStock;
import org.asodev.monolithic.warehousemanagement.repository.ProductStockRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductStockService {
    private final ProductStockRepository productStockRepository;

    public ProductStock getStockInfo(Long productId) {
        log.info("Getting stock for product with id: {}", productId);
        ProductStock productStock = productStockRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productStock;
    }

    public void updateStock(Long productId, Integer quantity) {
        log.info("Updating stock for product with id: {}", productId);
        ProductStock productStock = productStockRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // TODO: use validation
        if (quantity < 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (productStock.getQuantity() - productStock.getReservedQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        productStock.setQuantity(productStock.getQuantity() - quantity);

        // update available quantity with the new quantity
        productStock.setAvailableQuantity(productStock.getQuantity() - productStock.getReservedQuantity());
        productStockRepository.save(productStock);
    }
}

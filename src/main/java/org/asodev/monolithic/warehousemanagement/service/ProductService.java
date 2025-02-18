package org.asodev.monolithic.warehousemanagement.service;

import lombok.extern.slf4j.Slf4j;
import org.asodev.monolithic.warehousemanagement.converter.ProductConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.ExceptionMessages;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.asodev.monolithic.warehousemanagement.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(CreateProductDTO createProductDTO) {
        Product product = ProductConverter.toProduct(createProductDTO);
        productRepository.save(product);
    }

    public void updateProduct(Long productID, CreateProductDTO productDTO) {
        Optional<Product> foundProduct = productRepository.findById(productID);
        if (foundProduct.isEmpty()) {
            log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
            throw new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }
        Product product = ProductConverter.toProduct(productDTO);
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setIsActive(false);
            productRepository.save(product);
        }
    }

    public ProductResponseDTO getProductById(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
            throw new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }

        return ProductConverter.toProductResponseDTO(product.get());
    }

    @Cacheable(value="totalProducts")
    public Long getTotalProducts() {
        return productRepository.count();
    }

    @Cacheable(value = "products")
    public Map<String, Object> getAllProducts(int limit, int offset) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findAllWithLimit(PageRequest.of(offset, limit));
        List<ProductResponseDTO> productResponseDTOS = products.parallelStream().map(ProductConverter::toProductResponseDTO).toList();
        response.put("products", productResponseDTOS);
        response.put("total", getTotalProducts());
        return response;
    }
}

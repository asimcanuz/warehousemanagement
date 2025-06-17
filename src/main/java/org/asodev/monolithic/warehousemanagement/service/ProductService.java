package org.asodev.monolithic.warehousemanagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.asodev.monolithic.warehousemanagement.converter.CategoryConverter;
import org.asodev.monolithic.warehousemanagement.converter.ProductConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.ProductFilterDto;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CategoryResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.ExceptionMessages;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.asodev.monolithic.warehousemanagement.repository.ProductRepository;
import org.asodev.monolithic.warehousemanagement.specification.ProductSpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductStockService productStockService;

    @CachePut(value = "products", key = "#createProductDTO.name")
    public void createProduct(CreateProductDTO createProductDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(createProductDTO.getCategoryId());
        Category category = CategoryConverter.toCategory(categoryResponseDTO);

        Product product = Product.builder()
                .name(createProductDTO.getName())
                .price(createProductDTO.getPrice())
                .productStock(null)
                .category(category)
                .description(createProductDTO.getDescription())
                .isActive(createProductDTO.getIsActive())
                .build();
        productRepository.save(product);
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(Long productId, UpdateProductDTO updateProductDTO) {
        log.info("Updating product with ID: {}", productId);

        Product product = findProductById(productId);
        product.setName(updateProductDTO.name());

        product.setPrice(updateProductDTO.price());

        product.setDescription(updateProductDTO.description());

        if (updateProductDTO.isActive() != null) {
            product.setIsActive(updateProductDTO.isActive());
        }

        if (updateProductDTO.categoryId() != null) {
            product.setCategory(getCategory(updateProductDTO.categoryId()));
        }
        productStockService.updateStock(product.getId(), updateProductDTO.stock());

        productRepository.save(product);
        log.info("Product updated successfully: {}", product.getId());
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
                    return new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
                });
    }

    private Category getCategory(Long categoryId) {
        if (categoryId == null)
            return null;

        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(categoryId);
        return CategoryConverter.toCategory(categoryResponseDTO);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
            throw new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }
        productRepository.delete(product);
    }

    public ProductResponseDTO getProductById(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
            throw new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }

        return ProductConverter.toProductResponseDTO(product.get());
    }

    @Cacheable(value = "products")
    public Map<String, Object> getAllProducts(int limit, int offset, ProductFilterDto filter) {
        Map<String, Object> response = new HashMap<>();
        Page<Product> products;
        if (filter != null) {
            Specification<Product> specification = ProductSpecification.filterProducts(filter);
           products = productRepository.findAll(specification,PageRequest.of(offset, limit));
        }
        else {
            products = productRepository.findAll(PageRequest.of(offset, limit));
        }

        List<ProductResponseDTO> productResponseDTOS = products.stream()
                .map(ProductConverter::toProductResponseDTO).toList();

        response.put("products", productResponseDTOS);
        response.put("total", productRepository.count());
        return response;
    }

}

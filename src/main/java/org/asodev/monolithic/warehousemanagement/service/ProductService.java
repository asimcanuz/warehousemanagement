package org.asodev.monolithic.warehousemanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asodev.monolithic.warehousemanagement.converter.CategoryConverter;
import org.asodev.monolithic.warehousemanagement.converter.ProductConverter;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateProductDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CategoryResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.ProductResponseDTO;
import org.asodev.monolithic.warehousemanagement.exception.ExceptionMessages;
import org.asodev.monolithic.warehousemanagement.exception.WMSException;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.asodev.monolithic.warehousemanagement.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @CacheEvict(value = "products", allEntries = true)
    public void createProduct(CreateProductDTO createProductDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(createProductDTO.getCategoryId());
        Category category = CategoryConverter.toCategory(categoryResponseDTO);

        Product product = Product.builder()
                .name(createProductDTO.getName())
                .price(createProductDTO.getPrice())
                .quantity(createProductDTO.getQuantity())
                .category(category)
                .description(createProductDTO.getDescription())
                .isActive(createProductDTO.getIsActive())
                .build();
        productRepository.save(product);
    }


    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(Long productID, CreateProductDTO productDTO) {

        Optional<Product> foundProduct = productRepository.findById(productID);

        if (foundProduct.isEmpty()) {
            log.error(ExceptionMessages.PRODUCT_NOT_FOUND);
            throw new WMSException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }
        Product product = foundProduct.get();

        CategoryResponseDTO categoryResponseDTO = categoryService.getCategoryById(productDTO.getCategoryId());
        Category category = CategoryConverter.toCategory(categoryResponseDTO);

        product.setName(productDTO.getName()!=null ? productDTO.getName() : product.getName());
        product.setPrice(productDTO.getPrice() != null ? productDTO.getPrice() : product.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategory(productDTO.getCategoryId() != null ? category : product.getCategory());
        product.setDescription(productDTO.getDescription() != null ? productDTO.getDescription() : product.getDescription());
        product.setIsActive(productDTO.getIsActive() != null ? productDTO.getIsActive() : product.getIsActive());
        productRepository.save(product);
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
    public Map<String, Object> getAllProducts(int limit, int offset) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findAllWithLimit(PageRequest.of(offset, limit));
        List<ProductResponseDTO> productResponseDTOS = products.parallelStream().map(ProductConverter::toProductResponseDTO).toList();
        response.put("products", productResponseDTOS);
        response.put("total", productRepository.count());
        return response;
    }
}

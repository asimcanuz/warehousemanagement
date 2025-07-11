package org.asodev.monolithic.warehousemanagement;

import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.asodev.monolithic.warehousemanagement.repository.CategoryRepository;
import org.asodev.monolithic.warehousemanagement.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehousemanagementApplication implements CommandLineRunner {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public WarehousemanagementApplication(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);

		System.out.println("Warehouse Management Application started successfully.");
		System.out.println("Visit http://localhost:8080/swagger-ui.html for API documentation.");
	}

	@Override
	public void run(String... args) throws Exception {

		Category category1 = Category.builder()
				.name("Category 1")
				.description("Description for Category 1")
				.isActive(true)
				.build();

		Category category2 = Category.builder()
				.name("Category 2")
				.description("Description for Category 2")
				.isActive(true)
				.build();

		Category category3 = Category.builder()
				.name("Category 3")
				.description("Description for Category 3")
				.isActive(true)
				.parentCategory(category2)
				.build();

		categoryRepository.save(category1);
		categoryRepository.save(category2);
		categoryRepository.save(category3);

		Product product1 = Product.builder()
				.name("Product 1")
				.price(100.0)
				.description("Description for Product 1")
				.isActive(true)
				.build();
		Product product2 = Product.builder()
				.name("Product 2")
				.price(200.0)
				.description("Description for Product 2")
				.isActive(true)
				.build();

		Product product3 = Product.builder()
				.name("Product 3")
				.price(300.0)
				.description("Description for Product 3")
				.isActive(true)
				.category(category2)
				.build();
		Product product4 = Product.builder()
				.name("Product 4")
				.price(400.0)
				.description("Description for Product 4")
				.isActive(true)
				.category(category3)
				.build();

		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
		productRepository.save(product4);
	}
}

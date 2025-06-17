package org.asodev.monolithic.warehousemanagement;

import com.opencsv.CSVReader;
import org.asodev.monolithic.warehousemanagement.model.Category;
import org.asodev.monolithic.warehousemanagement.model.File;
import org.asodev.monolithic.warehousemanagement.model.Product;
import org.asodev.monolithic.warehousemanagement.repository.CategoryRepository;
import org.asodev.monolithic.warehousemanagement.repository.FileRepository;
import org.asodev.monolithic.warehousemanagement.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootApplication
public class WarehousemanagementApplication implements CommandLineRunner {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final FileRepository fileRepository;
	private static final Logger log = LoggerFactory.getLogger(WarehousemanagementApplication.class);


	public WarehousemanagementApplication(ProductRepository productRepository, CategoryRepository categoryRepository,  FileRepository fileRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.fileRepository = fileRepository;
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

		readFileMetadataFromCsv();

	}

	private void readFileMetadataFromCsv() {
		String csvFileName = "file_metadata.csv";
		Path csvFilePath = Paths.get(System.getProperty("java.io.tmpdir"), csvFileName);

		if (!Files.exists(csvFilePath)) {
			log.info("CSV file not found at: {}, skipping file metadata import", csvFilePath);
			return;
		}

		try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath.toFile()))) {
			String[] header = csvReader.readNext(); // Skip header
			if (header == null) {
				log.warn("Empty CSV file found at: {}", csvFilePath);
				return;
			}

			String[] line;
			while ((line = csvReader.readNext()) != null) {
				try {
					File file = File.builder()
							.id(Long.parseLong(line[0]))
							.filename(line[1])
							.originalFilename(line[2])
							.contentType(line[3])
							.size(Long.parseLong(line[4]))
							.entityType(line[5])
							.entityId(Long.parseLong(line[6]))
							.filePath(line[7])
							.fileUrl(line[8])
							.build();

					fileRepository.save(file);
					log.info("Imported file metadata: {}", file.getFilename());
				} catch (Exception e) {
					log.error("Failed to import file metadata from CSV row: {}", Arrays.toString(line), e);
				}
			}
			log.info("Completed importing file metadata from CSV");
		} catch (Exception e) {
			log.error("Failed to read CSV file: {}", csvFilePath, e);
		}
	}

}

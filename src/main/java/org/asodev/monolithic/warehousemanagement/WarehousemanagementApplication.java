package org.asodev.monolithic.warehousemanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class WarehousemanagementApplication {
	private static final Logger log = LoggerFactory.getLogger(WarehousemanagementApplication.class);

	private final WarehouseService warehouseService;

	public WarehousemanagementApplication(WarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}

	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner createData(JdbcTemplate jdbcTemplate) {
		return args -> {
			log.info("Starting data creation...");
			warehouseService.createCategories(jdbcTemplate);
			warehouseService.createProducts(jdbcTemplate);
			log.info("Data creation completed.");
		};
	}
}


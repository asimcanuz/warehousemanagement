package org.asodev.monolithic.warehousemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class WarehousemanagementApplication {

	@Autowired
	private WarehouseService warehouseService;


	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner createData(JdbcTemplate jdbcTemplate) {
		return args -> {
			warehouseService.createCategories(jdbcTemplate);
			warehouseService.createProducts(jdbcTemplate);
		};
	}
}


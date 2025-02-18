package org.asodev.monolithic.warehousemanagement;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class WarehousemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner createData(JdbcTemplate jdbcTemplate) {
		return args -> {
			createCategories(jdbcTemplate);
			createProducts(jdbcTemplate);
		};
	}

	@Transactional
	public void createCategories(JdbcTemplate jdbcTemplate) {
		String sql = "INSERT INTO categories (name, description, is_active) VALUES (?, ?, ?)";
		List<Object[]> batchArgs = new ArrayList<>();
		for (int i = 1; i <= 1000; i++) {
			batchArgs.add(new Object[]{"Category " + i, "Description for category " + i, true});
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Transactional
	public void createProducts(JdbcTemplate jdbcTemplate) {
		String sql = "INSERT INTO products (name, description, price, quantity, category_id, is_active) VALUES (?, ?, ?, ?, ?, ?)";
		List<Object[]> batchArgs = new ArrayList<>();
		for (int i = 1; i <= 1000000; i++) { // Adjust the batch size as needed
			batchArgs.add(new Object[]{
					"Product " + i,
					"Description for product " + i,
					Math.random() * 100,
					(int) (Math.random() * 1000),
					(int) (Math.random() * 1000) % 1000 + 1,
					true
			});
			if (i % 10000 == 0) { // Adjust the batch size as needed
				jdbcTemplate.batchUpdate(sql, batchArgs);
				batchArgs.clear();
			}
		}
		if (!batchArgs.isEmpty()) {
			jdbcTemplate.batchUpdate(sql, batchArgs);
		}
	}
}


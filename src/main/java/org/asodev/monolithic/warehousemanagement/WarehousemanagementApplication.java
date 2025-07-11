package org.asodev.monolithic.warehousemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WarehousemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);

		System.out.println("Warehouse Management Application started successfully.");
		System.out.println("Visit http://localhost:8080/swagger-ui.html for API documentation.");
	}

}

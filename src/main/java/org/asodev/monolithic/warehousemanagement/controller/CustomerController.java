package org.asodev.monolithic.warehousemanagement.controller;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @PostMapping
  public ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
    customerService.save(createCustomerDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}

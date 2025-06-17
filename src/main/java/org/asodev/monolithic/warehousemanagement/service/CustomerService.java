package org.asodev.monolithic.warehousemanagement.service;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.model.Customer;
import org.asodev.monolithic.warehousemanagement.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;

  public void save(CreateCustomerDTO createCustomerDTO) {
    log.info("Saving customer: {}", createCustomerDTO);

    try {
      Customer customer = Customer.builder()
          .name(createCustomerDTO.name())
          .email(createCustomerDTO.email())
          .type(createCustomerDTO.type())
          .phone(createCustomerDTO.phone())
          .build();

      customerRepository.save(customer);
    } catch (Exception e) {
      log.error("Error saving customer: {}", createCustomerDTO, e);
      throw e;
    }
  }

}

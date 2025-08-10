package org.asodev.monolithic.warehousemanagement.controller;

import java.util.List;
import java.util.Map;

import org.asodev.monolithic.warehousemanagement.dto.request.CreateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.CreateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.CustomerFilterDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateAddressDTO;
import org.asodev.monolithic.warehousemanagement.dto.request.UpdateCustomerDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.AddressResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.CustomerResponseDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.GenericResponse;
import org.asodev.monolithic.warehousemanagement.model.CustomerType;
import org.asodev.monolithic.warehousemanagement.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Customer Management", description = "Operations related to customer management")
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping
  @Operation(summary = "Create new customer", description = "Add a new customer to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Customer successfully created"),
      @ApiResponse(responseCode = "400", description = "Invalid customer data provided"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<CustomerResponseDTO> createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
    CustomerResponseDTO createdCustomer = customerService.createCustomer(createCustomerDTO);
    return GenericResponse.success(createdCustomer, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get customer by ID", description = "Retrieve a specific customer by their ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer found"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
    CustomerResponseDTO customer = customerService.getCustomerById(id);
    return GenericResponse.success(customer);
  }

  @GetMapping
  @Operation(summary = "Get all customers", description = "Retrieve customers with pagination and optional filtering")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<Map<String, Object>> getAllCustomers(
      @RequestParam(defaultValue = "10") @Min(1) @Max(100) int limit,
      @RequestParam(defaultValue = "0") @Min(0) int offset,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) CustomerType type,
      @RequestParam(required = false) Boolean isActive,
      @RequestParam(required = false) String phone,
      @RequestParam(required = false) String contactPerson) {
    CustomerFilterDTO filter = new CustomerFilterDTO(name, email, type, isActive, phone, contactPerson);
    Map<String, Object> customers = customerService.getAllCustomers(limit, offset, filter);
    return GenericResponse.success(customers);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update customer", description = "Update all details of an existing customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer successfully updated"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "400", description = "Invalid customer data provided"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<CustomerResponseDTO> updateCustomer(
      @PathVariable Long id,
      @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
    CustomerResponseDTO updatedCustomer = customerService.updateCustomer(id, updateCustomerDTO);
    return GenericResponse.success(updatedCustomer);
  }

  @PatchMapping("/{id}")
  @Operation(summary = "Soft delete customer", description = "Soft delete a customer by setting isActive to false")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<Void> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
    return GenericResponse.success(null);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Hard delete customer", description = "Permanently delete a customer from the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer permanently deleted"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<Void> hardDeleteCustomer(@PathVariable Long id) {
    customerService.hardDeleteCustomer(id);
    return GenericResponse.success(null);
  }

  @GetMapping("/active")
  @Operation(summary = "Get active customers", description = "Retrieve all active customers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Active customers retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<List<CustomerResponseDTO>> getActiveCustomers() {
    List<CustomerResponseDTO> activeCustomers = customerService.getActiveCustomers();
    return GenericResponse.success(activeCustomers);
  }

  @GetMapping("/type/{type}")
  @Operation(summary = "Get customers by type", description = "Retrieve customers filtered by customer type")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customers retrieved successfully"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<List<CustomerResponseDTO>> getCustomersByType(@PathVariable CustomerType type) {
    List<CustomerResponseDTO> customers = customerService.getCustomersByType(type);
    return GenericResponse.success(customers);
  }

  // Address management endpoints

  @PostMapping("/{customerId}/addresses")
  @Operation(summary = "Add address to customer", description = "Add a new address to an existing customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address successfully added"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "400", description = "Invalid address data provided"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<CustomerResponseDTO> addAddressToCustomer(
      @PathVariable Long customerId,
      @Valid @RequestBody CreateAddressDTO addressDTO) {
    CustomerResponseDTO customer = customerService.addAddressToCustomer(customerId, addressDTO);
    return GenericResponse.success(customer);
  }

  @PutMapping("/{customerId}/addresses/{addressId}")
  @Operation(summary = "Update customer address", description = "Update an existing address for a customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address successfully updated"),
      @ApiResponse(responseCode = "404", description = "Customer or address not found"),
      @ApiResponse(responseCode = "400", description = "Invalid address data provided"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<CustomerResponseDTO> updateCustomerAddress(
      @PathVariable Long customerId,
      @PathVariable Long addressId,
      @Valid @RequestBody UpdateAddressDTO addressDTO) {
    CustomerResponseDTO customer = customerService.updateCustomerAddress(customerId, addressId, addressDTO);
    return GenericResponse.success(customer);
  }

  @DeleteMapping("/{customerId}/addresses/{addressId}")
  @Operation(summary = "Delete customer address", description = "Remove an address from a customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Customer or address not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<Void> deleteCustomerAddress(
      @PathVariable Long customerId,
      @PathVariable Long addressId) {
    customerService.deleteCustomerAddress(customerId, addressId);
    return GenericResponse.success(null);
  }

  @GetMapping("/{customerId}/addresses")
  @Operation(summary = "Get customer addresses", description = "Retrieve all addresses for a specific customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer addresses retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<List<AddressResponseDTO>> getCustomerAddresses(@PathVariable Long customerId) {
    List<AddressResponseDTO> addresses = customerService.getCustomerAddresses(customerId);
    return GenericResponse.success(addresses);
  }

  @GetMapping("/{customerId}/addresses/active")
  @Operation(summary = "Get customer active addresses", description = "Retrieve all active addresses for a specific customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer active addresses retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Customer not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public GenericResponse<List<AddressResponseDTO>> getCustomerActiveAddresses(@PathVariable Long customerId) {
    List<AddressResponseDTO> addresses = customerService.getCustomerActiveAddresses(customerId);
    return GenericResponse.success(addresses);
  }
}

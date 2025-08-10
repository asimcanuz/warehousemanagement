package org.asodev.monolithic.warehousemanagement.controller;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.dto.request.WarehouseCreateDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.WarehouseResponseDTO;
import org.asodev.monolithic.warehousemanagement.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

  private final WarehouseService warehouseService;

  public WarehouseController(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GetMapping
  public ResponseEntity<List<WarehouseResponseDTO>> findAll() {
    List<WarehouseResponseDTO> response = warehouseService.findAll();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WarehouseResponseDTO> findById(@PathVariable Long id) {
    WarehouseResponseDTO response = warehouseService.findById(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<WarehouseResponseDTO> create(
      @RequestBody WarehouseCreateDTO dto) {
    WarehouseResponseDTO response = warehouseService.create(dto);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<WarehouseResponseDTO> update(@PathVariable Long id,
      @RequestBody WarehouseCreateDTO dto) {
    WarehouseResponseDTO response = warehouseService.update(id, dto);
    return ResponseEntity.ok(response);
  }

}

package org.asodev.monolithic.warehousemanagement.service;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.dto.request.WarehouseCreateDTO;
import org.asodev.monolithic.warehousemanagement.dto.response.WarehouseResponseDTO;
import org.asodev.monolithic.warehousemanagement.model.Warehouse;
import org.asodev.monolithic.warehousemanagement.model.WarehouseStatus;
import org.asodev.monolithic.warehousemanagement.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

  private final WarehouseRepository warehouseRepository;

  public WarehouseService(WarehouseRepository warehouseRepository) {
    this.warehouseRepository = warehouseRepository;
  }

  public List<WarehouseResponseDTO> findAll() {
    return warehouseRepository.findAll().stream()
        .map(this::mapToDTO)
        .toList();
  }

  public WarehouseResponseDTO findById(Long id) {
    return warehouseRepository.findById(id)
        .map(this::mapToDTO)
        .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
  }

  public WarehouseResponseDTO create(Warehouse warehouse) {
    Warehouse saved = warehouseRepository.save(warehouse);
    return mapToDTO(saved);
  }

  public WarehouseResponseDTO create(org.asodev.monolithic.warehousemanagement.dto.request.WarehouseCreateDTO dto) {
    Warehouse warehouse = map(dto);
    Warehouse saved = warehouseRepository.save(warehouse);
    return mapToDTO(saved);
  }

  public WarehouseResponseDTO update(Long id, WarehouseCreateDTO dto) {
    Warehouse warehouse = warehouseRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + id));
    warehouse.setCode(dto.code());
    warehouse.setName(dto.name());
    warehouse.setStatus(WarehouseStatus.valueOf(dto.status()));
    warehouse.setTimezone(dto.timezone());
    warehouse.setAddress(dto.address());
    Warehouse saved = warehouseRepository.save(warehouse);
    return mapToDTO(saved);
  }

  private Warehouse map(WarehouseCreateDTO dto) {
    Warehouse warehouse = new Warehouse();
    warehouse.setCode(dto.code());
    warehouse.setName(dto.name());
    warehouse.setStatus(WarehouseStatus.valueOf(dto.status()));
    warehouse.setTimezone(dto.timezone());
    warehouse.setAddress(dto.address());
    return warehouse;
  }

  private WarehouseResponseDTO mapToDTO(Warehouse warehouse) {
    return new WarehouseResponseDTO(
        warehouse.getId(),
        warehouse.getCode(),
        warehouse.getName(),
        warehouse.getStatus().name(),
        warehouse.getTimezone(),
        warehouse.getCreatedAt().toString(),
        warehouse.getUpdatedAt().toString(),
        warehouse.getVersion());
  }

}

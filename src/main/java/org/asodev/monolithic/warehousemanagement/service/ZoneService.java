package org.asodev.monolithic.warehousemanagement.service;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.Zone;
import org.asodev.monolithic.warehousemanagement.repository.ZoneRepository;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {
  private final ZoneRepository zoneRepository;

  public ZoneService(ZoneRepository zoneRepository) {
    this.zoneRepository = zoneRepository;
  }

  public List<Zone> findAll() {
    return zoneRepository.findAll();
  }

  public Zone findById(Long id) {
    return zoneRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
  }

  public Zone create(Zone zone) {
    return zoneRepository.save(zone);
  }

  public Zone update(Long id, Zone zone) {
    Zone existing = findById(id);
    existing.setCode(zone.getCode());
    existing.setName(zone.getName());
    existing.setType(zone.getType());
    existing.setWarehouse(zone.getWarehouse());
    return zoneRepository.save(existing);
  }

  public void delete(Long id) {
    zoneRepository.deleteById(id);
  }
}

package org.asodev.monolithic.warehousemanagement.service;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.Location;
import org.asodev.monolithic.warehousemanagement.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
  private final LocationRepository locationRepository;

  public LocationService(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public List<Location> findAll() {
    return locationRepository.findAll();
  }

  public Location findById(Long id) {
    return locationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Location not found with id: " + id));
  }

  public Location create(Location location) {
    return locationRepository.save(location);
  }

  public Location update(Long id, Location location) {
    Location existing = findById(id);
    existing.setCode(location.getCode());
    existing.setType(location.getType());
    existing.setCapacity(location.getCapacity());
    existing.setZone(location.getZone());
    existing.setWarehouse(location.getWarehouse());
    return locationRepository.save(existing);
  }

  public void delete(Long id) {
    locationRepository.deleteById(id);
  }
}

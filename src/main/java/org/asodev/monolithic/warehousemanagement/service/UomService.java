package org.asodev.monolithic.warehousemanagement.service;

import java.util.List;

import org.asodev.monolithic.warehousemanagement.model.Uom;
import org.asodev.monolithic.warehousemanagement.repository.UomRepository;
import org.springframework.stereotype.Service;

@Service
public class UomService {
  private final UomRepository uomRepository;

  public UomService(UomRepository uomRepository) {
    this.uomRepository = uomRepository;
  }

  public List<Uom> findAll() {
    return uomRepository.findAll();
  }

  public Uom findById(Long id) {
    return uomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Uom not found with id: " + id));
  }

  public Uom create(Uom uom) {
    return uomRepository.save(uom);
  }

  public Uom update(Long id, Uom uom) {
    Uom existing = findById(id);
    existing.setCode(uom.getCode());
    existing.setName(uom.getName());
    return uomRepository.save(existing);
  }

  public void delete(Long id) {
    uomRepository.deleteById(id);
  }
}

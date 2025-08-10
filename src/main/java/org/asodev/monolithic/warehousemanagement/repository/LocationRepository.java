package org.asodev.monolithic.warehousemanagement.repository;

import org.asodev.monolithic.warehousemanagement.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}

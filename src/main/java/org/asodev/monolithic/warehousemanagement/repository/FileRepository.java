package org.asodev.monolithic.warehousemanagement.repository;

import org.asodev.monolithic.warehousemanagement.model.EntityType;
import org.asodev.monolithic.warehousemanagement.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByEntityTypeAndEntityId(EntityType entityType, Long entityId);

}

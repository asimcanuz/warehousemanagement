package org.asodev.monolithic.warehousemanagement.model;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel implements Auditable<String, Long, LocalDateTime> {
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Optional<LocalDateTime> getCreatedDate() {
        return Optional.ofNullable(createdAt);
    }

    @Override
    public void setCreatedDate(LocalDateTime creationDate) {
        this.createdAt = creationDate;
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(updatedBy);
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.updatedBy = lastModifiedBy;
    }

    @Override
    public Optional<LocalDateTime> getLastModifiedDate() {
        return Optional.ofNullable(updatedAt);
    }

    @Override
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.updatedAt = lastModifiedDate;
    }

    @Override
    public abstract Long getId();

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}